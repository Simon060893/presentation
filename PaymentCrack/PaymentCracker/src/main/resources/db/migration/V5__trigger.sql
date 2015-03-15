CREATE OR REPLACE TRIGGER invalid_drop
before DELETE on Attributes
for each row
DECLARE
error EXCEPTION;
BEGIN
if Deleting THEN
raise error;
end if;
 EXCEPTION
				when error then
	RAISE_APPLICATION_ERROR(-20301, 'you can not make any deletes on Attributes' || MY_JOB_TITLE);
	end;
/

CREATE OR REPLACE TRIGGER invalid_drop1
before DELETE on Objects
for each row
	DECLARE
			error EXCEPTION;
	BEGIN
		if Deleting THEN
			raise error;
		end if;
		EXCEPTION
		when error then
		RAISE_APPLICATION_ERROR(-20301, 'you can not make any deletes on Objects' || MY_JOB_TITLE);
	end;
/

CREATE OR REPLACE TRIGGER invalid_drop2
before DELETE on ObjType
for each row
	DECLARE
			error EXCEPTION;
	BEGIN
		if Deleting THEN
			raise error;
		end if;
		EXCEPTION
		when error then
		RAISE_APPLICATION_ERROR(-20301, 'you can not make any deletes on ObjType' || MY_JOB_TITLE);
	end;
/

CREATE OR REPLACE TRIGGER invalid_drop3
before DELETE on ObjReference
for each row
	DECLARE
			error EXCEPTION;
	BEGIN
		if Deleting THEN
			raise error;
		end if;
		EXCEPTION
		when error then
		RAISE_APPLICATION_ERROR(-20301, 'you can not make any deletes on ObjReference' || MY_JOB_TITLE);
	end;
/

CREATE OR REPLACE TRIGGER invalid_drop4
before DELETE on Parameters
for each row
	DECLARE
			error EXCEPTION;
	BEGIN
		if Deleting THEN
			raise error;
		end if;
		EXCEPTION
		when error then
		RAISE_APPLICATION_ERROR(-20301, 'you can not make any deletes on Parameters' || MY_JOB_TITLE);
	end;
/