/*1. Создаем структуру бд на основе EAV-модели:
	а)OBJTYPE - для хранения уникальных имен(CODE) классов, идентефицированых уникальным числовым значением(OBJECT_TYPE_ID), если для нашего приложения
		предусмотрена иерархия(наследование) классов, для этого указываем дочернему классу его радителя(PARENT_ID), также для краткого руководства класса
		описываем его в поле(DESCRIPTION)
	б)ATTRTYPE - для хранения полей(CODE) класса, идентефицированых уникальным числовым значением(ATTR_ID), а также поле(ATTR_TYPE_ID) указывающий на класс,
		который содержит данное поле,
	в)OBJECTS - для хранения самих сущностей(обьектов), которые испольхуется в прилжении,идентефицируються уникальным числовым значением(OBJECT_ID),
		принадлжность к какому типу класса указывается в поле(OBJECT_TYPE_ID), обьект именуется(NAME) -уникально, если набблюдается какая то связь
		объектов то можно указать в поле (PARENT_ID) id объекта с которым наш созданый объект  будет связан
	г)Parametres-собственно здесь мы будем хранить  все значения(VALUE) объекта	(OBJECT_ID), у которого такие поля(ATTR_ID)
	д)OBJREFERENCE - чуть выше уже упомянуто, что между обьектами возможна связь(типо у Юзера есть кошелек), и эту связь мы можем указать сразу при
	создании оббекта в нашей БД, но что если у нашего приложения связь между объектами не одна(у Юзера может быть несколько кошельков, или у юзера есть платеж),
	для этого мы создаем в  (ATTRTYPE) новое поле класса объекта с которыми мы будем говорить нашему обькту, что у него есть еще одно поле но уже тип
	этого поля объект созданый в нашей бд ,Все эти отношение прописываем в таблице (OBJREFERENCE) нашей бд, в (ATTR_ID) указываем id созданного поля класса
	объекта, по которому будет видно что наш обьект (REFERENCE) будет содержать(связан) с обьектом(OBJECT_ID)

	2.Заполняем наше вышесозданную бд случайными(в зависимости от полей) уникальными значениями
	И так, наше приложение работает благодаря нескольким сущностям и тесной связи между ними. Для того чтобы показать, что наш
	подготовленый фундамент работоспособен и что данные храняться так как прописанно в плане, протестируем заполнив нашу бд фига-тучей данными. Идем по порядку:
	создаем классы(типы) нашим объектам(1), потом  задаем поля к этим классам(2) все это пишем ручками, т.к. в дальнейшем разработчику необходимо кнкретно
	знать про каждый класс по отдельности(рандомно припосать имя поля класса и потом при выборке гадать какой из них что будет, для этого потребуется отдельная
	документация),и следующим шагом запускаем процедуру из пакета(3), котороя сама в свою оередь тоже по отдельности запускает другие процедуры/функции для
	генерации данных и на этом как бы все!!! Для генерации и заполнения БД мы создали пару процедур и функций которых сохранили в одном пакете(6) my_package.
	Начнем с самого элемнтарного создания sequence(5) для объектов юзер,кошелек,платеж, будем перебирать один sequence чтобы не возникло проблемы у двух разных
	обьектов одинаковые id, следующим шагом создадим анонимную процедуру, в которой обьяляються 3 обьектных типа коллекций для каждой сущности, в которой мы нагенирим
	данные, далее сами коллекци для обьектов юзер и кошелек, следующие коллекции для обьектов таблиц PARAMETRES & OBJREFERENCE. Начинаем с заполнении коллекции состоящия
из оюьектов юзров, при чем количество обьектов мы заранее обьявили в конствнте 	iterations типа PLS_INTEGER(таким же типом проиндексированы наши коллекции ну по причине
что на хранение binary_integer нужно меньше разрядов, чем на Integer, это типо как в java int/Integer),потом для каждого юзера генерим знач для полей обьектами
юзера,предварительно зная индексы полей данного обьекта(1..9), во втором цикле создаем по purs_per_user кошельков на одного пользователя
и сразу формируем связь каждого созданного кошелька к текущнму юзеру,и также дополняем нашу Parametres значениями полей для кошельков, и завершающим этапом передаем
гаши заполненые коллекции оператору forall(т.к.коллекция это из pl/sql модуля то быстрее ее перебирать будет родными способами для её модуля, forall -как раз для pl/sql,
это клнечнл не причина, ведь обычный for тоже для pl/sql, в общем forall что и в jave foreach более подходящий вариант для коллекции) и на этом все.
*/

/* (1)initiate classes*/
/*
INSERT INTO OBJTYPE  VALUES (1,'Users',NULL);
INSERT INTO OBJTYPE  VALUES (2,'Currency',NULL);
INSERT INTO OBJTYPE  VALUES (3,'Purse',NULL);
INSERT INTO OBJTYPE  VALUES (4,'CreditCard',NULL);
INSERT INTO OBJTYPE  VALUES (5,'Transactions',NULL);
INSERT INTO OBJTYPE  VALUES (6,'Messages',NULL);*/

/* (2)initiate fields for classes*/
/*

INSERT INTO ATTRIBUTES  VALUES (1,'PASSWORD',1,1,NULL);
INSERT INTO ATTRIBUTES  VALUES (2,'FIO',1,1,NULL);
INSERT INTO ATTRIBUTES  VALUES (3,'PHONE_NUMBER',1,1,NULL);
INSERT INTO ATTRIBUTES  VALUES (4,'EMAIL',1,1,NULL);
INSERT INTO ATTRIBUTES  VALUES (5,'REGISTRATION_DATE',2,1,NULL);
INSERT INTO ATTRIBUTES  VALUES (6,'IS_ACTIVE',3,1,NULL);
INSERT INTO ATTRIBUTES  VALUES (7,'IS_BANNED',3,1,NULL);
INSERT INTO ATTRIBUTES  VALUES (8,'IS_ADMINISTRATOR',3,1,NULL);
INSERT INTO ATTRIBUTES  VALUES (9,'CURRENCY_LABEL',1,2,NULL);
INSERT INTO ATTRIBUTES  VALUES (10,'CURRENCY_VALUE',4,2,NULL);
INSERT INTO ATTRIBUTES  VALUES (11,'CURRENCY_DATE',2,2,NULL);
INSERT INTO ATTRIBUTES  VALUES (12,'BALANCE',4,3,NULL);
INSERT INTO ATTRIBUTES  VALUES (13,'CURRENCY_ID',5,3,NULL);
INSERT INTO ATTRIBUTES  VALUES (14,'BALANCE',4,4,NULL);
INSERT INTO ATTRIBUTES  VALUES (15,'CURRENCY_ID',5,4,NULL);
INSERT INTO ATTRIBUTES  VALUES (16,'BALANCE',4,5,NULL);
INSERT INTO ATTRIBUTES  VALUES (17,'PURSE_WHERE',5,5,NULL);
INSERT INTO ATTRIBUTES  VALUES (18,'PURSE_FROM',5,5,NULL);
INSERT INTO ATTRIBUTES  VALUES (19,'MESSAGE_DATE',2,6,NULL);
INSERT INTO ATTRIBUTES  VALUES (20,'MESSAGE_TEXT',1,6,NULL);
INSERT INTO ATTRIBUTES  VALUES (21,'CONFIRM_TOKEN_MESSAGE',3,6,NULL);
*/

--(5)---------------------------******************************************--------------------------
CREATE SEQUENCE someIdn START WITH 1;
CREATE OR REPLACE FUNCTION createDate(num IN NUMBER)
  RETURN VARCHAR2
IS
  str VARCHAR2(20);
  BEGIN
   IF (num < 5) THEN
      str := dbms_random.string('U', 5) || someIdn.nextval;
    ELSIF num=5 THEN
      str := TO_CHAR(to_Date('2014-02-04', 'yyyy/dd/mm')+someIdn.currval,'yyyy-dd-mm');
    ELSIF num=6 THEN
      str := 'true';
    ELSE
        str := 'false';
    END IF;
    RETURN str;
  END;
/

CREATE OR REPLACE FUNCTION getCurrenId(num IN NUMBER)
  RETURN NUMBER
IS
  str VARCHAR(3);
  sum1 NUMBER;
  BEGIN
    IF (num = 1) THEN
      str := 'UAH';
    ELSIF (num=2) THEN
        str := 'USD';
    ELSE
      str := 'EUR';
    END IF;
    SELECT OBJECTID INTO sum1 FROM OBJECTS  WHERE NAME = str;
    RETURN sum1;
  END;
/

--create type of collection for user

DECLARE
  TYPE ObjTab IS TABLE OF OBJECTS%ROWTYPE INDEX BY PLS_INTEGER;
  TYPE ParTab IS TABLE OF ParametERs%ROWTYPE INDEX BY PLS_INTEGER;
  TYPE RefTab IS TABLE OF OBJREFERENCE%ROWTYPE INDEX BY PLS_INTEGER;
  last_added_Obj      NUMBER :=222222222222;
  last_added_Prm      NUMBER;
  user_objs           ObjTab;
  purs_objs           ObjTab;
  par_pars            ParTab;
  user_refs           RefTab;
  param_counts        NUMBER := 1;
  purs_per_user       NUMBER := 3;

  iterations CONSTANT PLS_INTEGER := 10;
  idNameOfCyrrency  PLS_INTEGER := 0;
BEGIN
  FOR i IN 1..iterations LOOP
    last_added_Obj := last_added_Obj + 1;
    user_objs(i).OBJECTID := last_added_Obj;
    user_objs(i).OBJTYPEID := 1;
    user_objs(i).PARENTID := NULL;
    user_objs(i).NAME := dbms_random.string('U', 10);
    FOR j IN 1..8 LOOP
      par_pars(param_counts).Attrid := j;
      par_pars(param_counts).OBJECTID := last_added_Obj;
      par_pars(param_counts).VALUE := createDate(j);
      par_pars(param_counts).DateValue := to_Date('2014-02-04', 'yyyy/dd/mm') + j + i;
      param_counts := param_counts + 1;
    END LOOP;
  END LOOP;
  FOR i IN 1..user_objs.count LOOP
    FOR j IN purs_objs.count + 1..purs_objs.count + purs_per_user LOOP
      idNameOfCyrrency:=idNameOfCyrrency+1;
      last_added_Obj := last_added_Obj + 1;
      purs_objs(j).OBJECTID := last_added_Obj;
      purs_objs(j).OBJTYPEID := 3;
      purs_objs(j).parentid := user_objs(i).objectid;
      purs_objs(j).name := dbms_random.string('U', 10) || j;
      user_refs(j).ATTRID := 13;
      user_refs(j).REFERENCE := last_added_Obj;
      user_refs(j).OBJECTID := getCurrenId(idNameOfCyrrency);
        par_pars(param_counts).Attrid := 12;
        par_pars(param_counts).OBJECTID := last_added_Obj;
        par_pars(param_counts).VALUE := 0;
        par_pars(param_counts).DateValue := to_Date('2014-02-04', 'yyyy/dd/mm') + j + i;
        param_counts := param_counts + 1;

    END LOOP;
    idNameOfCyrrency:=0;
  END LOOP;

  FORALL i IN 1..user_objs.count
  INSERT INTO OBJECTS VALUES
    (user_objs(i).OBJECTID, user_objs(i).NAME, user_objs(i).OBJTYPEID, user_objs(i).PARENTID);

  FORALL i IN 1..purs_objs.count
  INSERT INTO OBJECTS VALUES
    (purs_objs(i).OBJECTID, purs_objs(i).NAME, purs_objs(i).OBJTYPEID, purs_objs(i).PARENTID);

  FORALL i IN 1..par_pars.count
  INSERT INTO ParametERs VALUES
    (par_pars(i).DateValue, par_pars(i).VALUE, par_pars(i).OBJECTID, par_pars(i).Attrid);

  FORALL i IN 1..user_refs.count
  INSERT INTO OBJREFERENCE VALUES
    (user_refs(i).REFERENCE, user_refs(i).OBJECTID, user_refs(i).Attrid);

END;
/
--110000 обьектов зп 2,5 миуты
--предпологаемые индексы
CREATE INDEX CODE_ID_ATTR ON ATTRIBUTES(NAME);
-- чтобы доставать Value из Parametres по названию поля класса
CREATE INDEX OBJECT_TYPE_OBJ ON OBJECTS(OBJTYPEID);
--доставать лбьект по типу(SELECT_ALL_OBJECTS_BY_TYPE)
CREATE INDEX NAME_OBJ ON OBJECTS(NAME);
--доставать лбьект по имени(V2__user_function.sql: 1-st select)
CREATE INDEX OBJECT_ID_PRM ON Parameters(OBJECTID);
 -- чтобы доставать Value из Parametres по обьекту (SELECT_PARAMETERS_BY_OBJECTS,SELECT_ALL_ENTITIES_BY_TYPE)
CREATE INDEX VALUE_PRM ON Parameters(VALUE);
-- индекс для данной колонки под сомнением (V2__user_function.sql: 2-st select)
CREATE INDEX OBJTYPEID_ATTR ON ATTRIBUTES(OBJTYPEID);
-- чтобы доставать Value из Parametres по обьекту (SELECT_ATTRIBUTES_BY_OBJTYPE)
