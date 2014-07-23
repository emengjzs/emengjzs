------------------------------------------------------------------
-- NJU SOFTWARE INSTITUTE COURSE DATABASE SYSTEM HOMEWORK3 ANSWER
-- @Author: emengjzs 
-- @Date:   14/07/22
------------------------------------------------------------------

-- ***************************************************************
-- 第一题解答
-- ***************************************************************
-- create schema  entrance_exam_db
-- city城市 
create table cities (
    city varchar(16) not null,
    primary key (city)
); 

-- schools 学校
create table schools (
    sno   char(4) not null,
    sname varchar(32) not null,
    city  varchar(16) not null,
    primary key (sno),
    constraint city_ref foreign key (city) references cities (city)
);

-- Student 学生
create table students (
	eno   integer not null, 				-- 考号
	ename varchar(10),
	sex   char(3) not null,
	sno   char(4),
	primary key (eno),
	constraint eno_ck   check(eno>=0),
	constraint sex_ck   check(sex='男' or sex='女'),
	constraint sno_ref  foreign key (sno) references schools (sno)
);
	
create table scores (
  eno     integer not null,
  etype   char(3),          -- 文/理
  
  chinese integer,
  math    integer,
  english integer,
  addition integer,         -- 附加分
  
  select1_name char(6),
  select1_score integer     check(select1_score between 0 and 120),
  select1_level varchar(4)  check(select1_level in ('A+', 'A', 'B+', 'B', 'C', 'D')),
  
  select2_name char(6),
  select2_score integer     check(select2_score between 0 and 120),
  select2_level varchar(4)  check(select2_level in ('A+', 'A', 'B+', 'B', 'C', 'D')),
  
  complus1_name char(6),
  complus1_score integer    check(complus1_score between 0 and 100),
  complus1_level char(2)     check(complus1_level in ('A', 'B', 'C', 'D')),
  
  complus2_name char(6),
  complus2_score integer    check(complus2_score between 0 and 100),
  complus2_level char(2)     check(complus2_level in ('A', 'B', 'C', 'D')),
  
  complus3_name char(6),
  complus3_score integer    check(complus3_score between 0 and 100),
  complus3_level char(2)    check(complus3_level in ('A', 'B', 'C', 'D')),
  
  complus4_name char(6),
  complus4_score integer    check(complus4_score between 0 and 100),
  complus4_level char(2)    check(complus4_level in ('A', 'B', 'C', 'D')),
  
  total   integer check(total >= 0),  -- 总分
  
  primary key (eno),
  constraint eno_ref      foreign key (eno) references students (eno),
  
  constraint etype_ck     check(etype in ('文', '理')),
  constraint s1_nck       check((select1_name = '历史' and etype = '文') or
                                  (select1_name = '物理' and etype = '理')),
                                  
  constraint s2_nck       check(select2_name in ('生物', '化学', '地理', '政治')),
  
  constraint chinese_ck   check(chinese   between 0 and 160),
  constraint math_ck      check(math      between 0 and 160),
  constraint english_ck   check(english   between 0 and 120),
  constraint addition_ck  check(addition  between 0 and 40)
 
  -- constraint tatal_ck   check(total=chinese+math+english+addition)
);

-- *******************************************************************************
-- 第二题解答
-- *******************************************************************************
update scores set complus1_level = case 
                                    when complus1_score between 90 and 100 then 'A'
                                    when complus1_score between 75 and 89  then 'B'
                                    when complus1_score between 60 and 74  then 'C'
                                    when complus1_score between 0  and 59  then 'D'
                                    end,
                   complus2_level = case 
                                    when complus2_score between 90 and 100 then 'A'
                                    when complus2_score between 75 and 89  then 'B'
                                    when complus2_score between 60 and 74  then 'C'
                                    when complus2_score between 0  and 59  then 'D'
                                    end,
                   complus3_level = case 
                                    when complus3_score between 90 and 100 then 'A'
                                    when complus3_score between 75 and 89  then 'B'
                                    when complus3_score between 60 and 74  then 'C'
                                    when complus3_score between 0  and 59  then 'D'
                                    end,
                   complus4_level = case 
                                    when complus4_score between 90 and 100 then 'A'
                                    when complus4_score between 75 and 89  then 'B'
                                    when complus4_score between 60 and 74  then 'C'
                                    when complus4_score between 0  and 59  then 'D'
                                    end;   
commit;
-- *******************************************************************************
-- 第三题解答
-- *******************************************************************************

--- ******************************************************************************
--- 子存储过程
--- SET_SELECT_LEVEL 调用 SET_LEVEL 完成第三要求
--- ******************************************************************************
create or replace PROCEDURE SET_LEVEL (subject IN char) AS 
-- utf-8
-- 登记选测科目等级
--  subject 科目名称
-- 若某一科目学生只有两位 则第一位成绩是A+还是B？？？
TYPE ptr IS REF CURSOR ;   
score_ptr ptr;
subject_index integer:=0; -- 科目1/2？        
score_index   float := 1;
score_radio   float := 0.0;
score_rank    char(4);
student_score integer;
last_score    integer := 121;

-- itr
student_no    integer;
sum_students  integer;
BEGIN

  -- 登记第一选测等级
  IF subject in ('物理', '历史') THEN
    BEGIN
      subject_index := 1;
      open score_ptr for select select1_score, eno from scores sc where select1_name = subject order by select1_score desc;
      select count(*) into sum_students from scores sc where sc.select1_name = subject;
    END;
   
  ELSIF subject in ('生物', '地理', '政治', '化学') THEN
    BEGIN
      subject_index := 2;
      open score_ptr for select select2_score, eno from scores sc where select2_name = subject order by select2_score desc;
      select count(*) into sum_students from scores sc where sc.select2_name = subject;
    END;
  ELSE 
    NULL;
  END IF;  
   
  LOOP
    FETCH score_ptr INTO student_score, student_no;
      EXIT WHEN score_ptr%NOTFOUND;
      
    IF student_score < last_score THEN
      BEGIN
        score_radio := score_index / sum_students; 
        dbms_output.put_line(score_radio);
        last_score := student_score;
      END;
    END IF;
    
    IF score_index = 1.0 THEN
      BEGIN
        score_radio := 0.0; 
      END;
    END IF;
    
    IF subject_index = 2 THEN
      BEGIN
        update scores set select2_level = case 
                                    when score_radio between 0.0 and 0.05 then 'A+'
                                    when score_radio between 0.05 and 0.2 then 'A'
                                    when score_radio between 0.2 and 0.3  then 'B+'
                                    when score_radio between 0.3 and 0.5  then 'B'
                                    when score_radio between 0.5 and 0.9  then 'C'
                                    else 'D'
                                    end
                    where scores.eno = student_no;
      END; 
    
    ELSIF subject_index = 1 THEN
      BEGIN
        update scores set select1_level = case 
                                    when score_radio between 0.0 and 0.05 then 'A+'
                                    when score_radio between 0.05 and 0.2 then 'A'
                                    when score_radio between 0.2 and 0.3  then 'B+'
                                    when score_radio between 0.3 and 0.5  then 'B'
                                    when score_radio between 0.5 and 0.9  then 'C'
                                    else 'D'
                                    end
                    where scores.eno = student_no;
      END;
    ELSE 
      NULL;
    END IF;
    
   
  score_index := score_index + 1.0;  
  END LOOP;
  close score_ptr;
  
END SET_LEVEL;


--- ******************************************************************************
--- 主存储过程
--- SET_SELECT_LEVEL 调用 SET_LEVEL 完成第三要求
--- ******************************************************************************
create or replace PROCEDURE SET_SELECT_LEVEL AS
subject char(6);
BEGIN
  subject := '物理';
  SET_LEVEL(subject);
  subject := '历史';
  SET_LEVEL(subject);
  subject := '生物';
  SET_LEVEL(subject);
  subject := '化学';
  SET_LEVEL(subject);
  subject := '政治';
  SET_LEVEL(subject);
  subject := '地理';
  SET_LEVEL(subject);
END SET_SELECT_LEVEL;

-- *******************************************************************************
-- 第四题解答
-- *******************************************************************************
create or replace PROCEDURE SET_SCORE AS 
-- 设计一个存储过程，用于计算每个考生的总分
--（包括‘必测’科目等级加分），并赋给表中的‘总
-- 分’字段
CURSOR score_ptr IS select eno, chinese, math, english, addition,
                            complus1_level, 
                            complus2_level, 
                            complus3_level, 
                            complus4_level
                      from scores;
s_eno integer;
s_chinese integer;
s_math integer;
s_english integer;
s_addition integer;
level_1 char(2);
level_2 char(2);
level_3 char(2);
level_4 char(2);
A_count integer := 0;
s_total integer := 0;
BEGIN
  open score_ptr;
  LOOP
    FETCH score_ptr INTO s_eno, s_chinese, s_math, s_english, s_addition, 
                          level_1, 
                          level_2, 
                          level_3,
                          level_4;
    EXIT WHEN score_ptr%NOTFOUND;
    
    -- 初始化
    A_count := 0;
    s_total := 0;
    
    -- 在4门必测科目中，有一个A则在总分中加1分
    IF level_1='A' THEN
      BEGIN
      A_count := A_count + 1;
      END;
    END IF;
    
    IF level_2='A' THEN
      BEGIN
      A_count := A_count + 1;
      END;
    END IF;
    
    IF level_3='A' THEN
      BEGIN
      A_count := A_count + 1;
      END;
    END IF;
    
    IF level_4='A' THEN
      BEGIN
      A_count := A_count + 1;
      END;
    END IF;
    
    -- 总分’是指语数外3门的分数、语文(或数学)附加题的分数，以及4门必测
    -- 科目的等级加分的总和
    s_total := s_chinese + s_math + s_english + s_addition + A_count;
    
    -- 如果4门必测科目的等级都是A（俗称4A），则另外再奖励1
    -- 分，即在总分中累计加5分。
    IF A_count=4 THEN
      BEGIN
      s_total := s_total + 1;
      END;
    END IF;
    
    update scores set total = s_total where s_eno = eno;  
    
  END LOOP;
  close score_ptr;
END SET_SCORE;


-- *******************************************************************************
-- 第五题解答
-- *******************************************************************************
create view 文科考生 (考生号, 姓名, 性别, 语文分, 数学分, 英语分, 附加分, 总分, 排序分, 
                          选测科目一, 选测一等级, 选测科目二, 选测二等级, 必测科目一, 必测一等级, 
                          必测科目二, 必测二等级, 必测科目三, 必测三等级, 必测科目四, 必测四等级
                          )
                          as select students.eno, students.ename, students.sex,
                                     scores.chinese,
                                     scores.math,
                                     scores.english,
                                     scores.addition,
                                     scores.total,
                                     scores.math+scores.chinese+scores.addition,
                                     select1_name,
                                     select1_level,
                                     select2_name,
                                     select2_level,
                                     complus1_name,
                                     complus1_level,
                                     complus2_name,
                                     complus2_level,
                                     complus3_name,
                                     complus3_level,
                                     complus4_name,
                                     complus4_level
                            from students, scores where scores.etype = '文' 
                                                   and students.eno = scores.eno;


create view 理科考生 (考生号, 姓名, 性别, 语文分, 数学分, 英语分, 附加分, 总分, 排序分, 
                          选测科目一, 选测一等级, 选测科目二, 选测二等级, 必测科目一, 必测一等级, 
                          必测科目二, 必测二等级, 必测科目三, 必测三等级, 必测科目四, 必测四等级
                          )
                          as select students.eno, students.ename, students.sex,
                                     scores.chinese,
                                     scores.math,
                                     scores.english,
                                     scores.addition,
                                     scores.total,
                                     scores.math+scores.chinese+scores.addition,
                                     select1_name,
                                     select1_level,
                                     select2_name,
                                     select2_level,
                                     complus1_name,
                                     complus1_level,
                                     complus2_name,
                                     complus2_level,
                                     complus3_name,
                                     complus3_level,
                                     complus4_name,
                                     complus4_level
                            from students, scores where scores.etype = '理' 
                                                   and students.eno = scores.eno;
-- ***********************************************************************************

