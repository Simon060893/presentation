create or replace FUNCTION checkIsUserUnique(
  IN_LOGIN IN OBJECTS.NAME%TYPE,
  IN_PASSWORD IN PARAMETERS.VALUE%TYPE)
  RETURN OBJECTS.OBJECTID%TYPE
IS
  VAR_USERS_COUNT NUMBER(2);
  VAR_USER_OBJECT_ID OBJECTS.OBJECTID%TYPE;
  VAR_USER_ID NUMBER(12);
  EXCEPTION_USER_IS_NOT_EXIST EXCEPTION;
  BEGIN
    SELECT ROW_NUMBER() OVER (ORDER BY OBJECTS.OBJECTID), OBJECTS.OBJECTID  AS "R" INTO VAR_USERS_COUNT, VAR_USER_OBJECT_ID
    FROM OBJECTS WHERE IN_LOGIN = OBJECTS.NAME;
    IF VAR_USERS_COUNT > 0 THEN
      BEGIN
        SELECT COUNT(PARAMETERS.OBJECTID)  INTO VAR_USERS_COUNT
        FROM PARAMETERS WHERE PARAMETERS.OBJECTID = VAR_USER_OBJECT_ID
                              AND IN_PASSWORD = PARAMETERS.VALUE;
        IF VAR_USERS_COUNT > 0 THEN
          BEGIN
            RETURN VAR_USER_OBJECT_ID;
          END;
        ELSE
          BEGIN
            RETURN -2;
          END;
        END IF;
        END;
    END IF;
    EXCEPTION WHEN NO_DATA_FOUND THEN
    BEGIN
      RETURN -1;
    END;
  END;
/