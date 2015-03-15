-- create or replace FUNCTION checkIsUserUnique(
--   IN_LOGIN IN PARAMETERS.VALUE%TYPE,
--   IN_PASSWORD IN PARAMETERS.VALUE%TYPE)
--   RETURN OBJECTS.OBJECTID%TYPE
-- IS
--   VAR_USER_OBJECT_ID OBJECTS.OBJECTID%TYPE;
--   BEGIN
--     with user_found as (SELECT OBJECTID  from Parameters where ATTRID = 4 and VALUE=IN_LOGIN)
--   SELECT u.OBJECTID  into VAR_USER_OBJECT_ID from Parameters p,user_found u
--   WHERE u.OBJECTID=p.OBJECTID and  p.ATTRID=1 and p.VALUE=IN_PASSWORD;
--     RETURN VAR_USER_OBJECT_ID;
--
--     EXCEPTION WHEN NO_DATA_FOUND THEN
--     BEGIN
--       RETURN -1;
--     END;
--   END;
-- /