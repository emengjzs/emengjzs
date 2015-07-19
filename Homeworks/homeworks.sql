-- phpMyAdmin SQL Dump
-- version 4.1.14
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: 2015-06-28 09:57:22
-- 服务器版本： 5.6.17
-- PHP Version: 5.5.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `homeworks`
--

-- --------------------------------------------------------

--
-- 表的结构 `admin`
--

CREATE TABLE IF NOT EXISTS `admin` (
  `id` varchar(45) NOT NULL DEFAULT 'admin',
  `password` varchar(45) NOT NULL DEFAULT 'admin',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- 转存表中的数据 `admin`
--

INSERT INTO `admin` (`id`, `password`) VALUES
('admin', '123456');

-- --------------------------------------------------------

--
-- 表的结构 `assignment`
--

CREATE TABLE IF NOT EXISTS `assignment` (
  `aid` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `lid` int(10) unsigned NOT NULL,
  `publish_date` datetime DEFAULT NULL,
  `deadline` datetime DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `description` text,
  `file_type` varchar(45) DEFAULT NULL,
  `difficulty` int(11) DEFAULT NULL,
  `score` float DEFAULT NULL,
  `check_deadline` datetime DEFAULT NULL,
  `status` int(11) DEFAULT '0',
  `summary` text,
  `file` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`aid`),
  KEY `fk_assignment_lesson1_idx` (`lid`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=21 ;

--
-- 转存表中的数据 `assignment`
--

INSERT INTO `assignment` (`aid`, `lid`, `publish_date`, `deadline`, `name`, `description`, `file_type`, `difficulty`, `score`, `check_deadline`, `status`, `summary`, `file`) VALUES
(8, 14, '2014-09-05 15:23:29', '2014-09-07 23:59:59', '迭代一', '<div><h2>以小组为单位 上交 需求开发阶段的所有制品：<br></h2><ol><li>需求分析模型 （包括每个用例的系统顺序图、全局的概念类图、适当的顺序图和状态图）如果来不及用工具，可以手画。  </li><li>软件需求规格说明文档（重点包括高优先级的功能需求，低优先级的可以放到作业10中提交，但必须包含其他文档模版中的其他要素）</li></ol></div>', 'zip', 4, 100, '2014-09-11 23:59:59', 3, NULL, NULL),
(9, 8, '2014-09-05 15:29:44', '2014-10-27 00:00:00', 'Assignment1', '<ul><li>l以小组为单位提交：</li><li>&nbsp;n分组名单（txt），要求4人一组，可参考《计算与软件工程》课程的分组方式（可选）</li><li>&nbsp;n重新审视《计算与软件工程》课程大作业，如有必要，可进行部分修改\n\nn提交《计算与软件工程》课程大作业带“数据拥有者”标记的类图（应用逻辑部分）（pdf）\n</li><li>\nn作业加成分（可选）（txt）</li></ul>', 'pdf', 4, 100, '2014-11-04 00:00:00', 1, NULL, NULL),
(10, 12, '2014-09-02 10:22:04', '2014-09-05 00:00:00', '项目展开阶段作业 ', '<div>​项目展开阶段作业 <br><br>提交内容：需求获取安排计划书；用例文档（用户需求文档）；用户需求列表；使用的素材（面谈准备材料和报告；原型脚本、制品和评估情况）。</div>', 'pdf', 6, 100, '2014-09-08 00:00:00', 2, NULL, NULL),
(11, 10, '2014-09-02 10:24:23', '2014-11-02 00:00:00', 'ucOS2', '<div>​<table border="1" cellspacing="0" width="100%"><tbody><tr><td>ucOSII下动态内存管理改进。可参照附件，也可自行查找资料。 <br>可选，Bonus。 <br><br>要求： <br><br>1. 在ucOSII在pc上的移植版本上实现，参见Lecture Notes下的实验目录下的uCOS-II目录下的版本。<br><br><br>2. 提交完整的文档说明，参考资料以及实现步骤，包括所增加的代码与注释。  </td></tr><tr><td>&nbsp;											 </td><td>												&nbsp; </td></tr></tbody></table></div>', 'rar', 3, 100, '2014-11-07 00:00:00', 2, NULL, NULL),
(12, 11, '2014-09-02 10:26:02', '2014-09-05 00:00:00', '1', '<div>​<table border="1" cellspacing="0" width="100%"><tbody><tr><td><br></td><td>请阅读并理解附件中的两段程序</td></tr></tbody></table></div>', 'pdf', 3, 100, '2014-09-10 00:00:00', 3, NULL, NULL),
(13, 11, '2014-09-02 10:26:02', '2014-09-05 00:00:00', '1', '<div>​<table border="1" cellspacing="0" width="100%"><tbody><tr><td><br></td><td>请阅读并理解附件中的两段程序</td></tr></tbody></table></div>', 'pdf', 3, 100, '2014-09-10 00:00:00', 3, NULL, NULL),
(14, 15, '2015-03-28 21:31:52', '2015-03-29 00:00:00', '需求文档', '<div>​写好需求文档</div>', 'doc', 3, 100, '2015-03-30 00:00:00', 2, NULL, NULL),
(15, 25, '2015-03-28 21:37:38', '2015-03-29 00:00:00', '作业3', '<div style="text-align: center;"><h2>​<span style="color: rgb(230, 145, 56);">详细设计文档</span></h2><p>1. 分小组完成详细设计文档</p><p>2.注意设计模式的使用、类图<br></p></div>', 'pdf', 4, 50, '2015-03-30 00:00:00', 3, NULL, NULL),
(16, 24, '2015-03-28 21:39:04', '2015-03-30 00:00:00', '作业1', '<div>​SELinux都有哪些特性？<br><br>没有SELinux保护的Linux的安全级别和Windows一样，是C2级，但经过SELinux保护的Linux安全级别可以达到B1级，下面是SELinux的一些特点：<br><br>1）强制访问控制MAC；2）类型加强TE（Type Enforcement）——对进程只赋予最小权限；3）进行类型的迁移——防止权限升级；4）基于角色的访问控制RBAC——对用户只赋予最小权限；5）SELinux策略——决定保护类别和方式。<br><br>&nbsp; 如何启动和禁止SELinux，都有哪些办法可以知道SELinux当前的运行状态？<br><br></div>', 'doc', 3, 5, '2015-04-01 00:00:00', 3, NULL, NULL),
(17, 24, '2015-03-28 21:40:02', '2015-04-01 00:00:00', '作业2', '<div>​SELinux AVC消息有什么作用？如何查看AVC消息？<br><br>AVC消息是由SELinux因访问拒绝或者因制定的审计规则而产生的审核消息，edora10默认安装并且打开了SELinux 诊断工具 setroubleshoot，这个工具为桌面用户提供了 SELinux 访问受限的通知和详细信息，以及如何处理它们的建议。当系统中的某个操作违反了SELinux的安全策略时，该工具在桌面上弹出AVC警报。当看到AVC警报时，可以双击该通知图标，以打开SeTroubleshoot浏览器，也可以通过菜单项【应用程序/系统工具/SELinux故障排除工具】来打开SeTroubleshoot浏览器，在SeTroubleshoot浏览器上面列出了所有检查到的AVC消息。<br><br></div>', 'doc', 3, 5, '2015-04-02 00:00:00', 2, 'ddddd \n                        \n                        ', '17/sample/英语阅读秘诀(二).doc'),
(18, 24, '2015-04-01 00:29:01', '2015-04-02 00:00:00', '作业6', '<div>​完成上机考试</div>', 'pdf', 3, 10, '2015-04-03 00:00:00', 2, NULL, NULL),
(19, 24, '2015-04-01 11:09:39', '2015-04-04 00:00:00', '作业4', '<div>​12</div>', 'doc', 1, 100, '2015-04-06 00:00:00', 2, NULL, NULL),
(20, 27, '2015-06-14 20:56:40', '2015-06-16 00:00:00', '作业3', '<div>​223</div>', 'doc', 6, 100, '2015-06-19 00:00:00', 1, NULL, NULL);

-- --------------------------------------------------------

--
-- 替换视图以便查看 `assignmentsummary`
--
CREATE TABLE IF NOT EXISTS `assignmentsummary` (
`name` varchar(255)
,`lid` int(10) unsigned
,`aid` int(10) unsigned
,`stunum` bigint(21)
,`aiisgnmentscore` float
,`maxscore` float
,`avgscore` double
,`avgrate` double
,`perfect` decimal(23,0)
,`greate` decimal(23,0)
,`good` decimal(23,0)
,`bad` decimal(23,0)
,`miss` decimal(23,0)
);
-- --------------------------------------------------------

--
-- 表的结构 `assign_submit_record`
--

CREATE TABLE IF NOT EXISTS `assign_submit_record` (
  `srid` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `sid` int(10) unsigned NOT NULL,
  `aid` int(10) unsigned NOT NULL,
  `lid` int(10) unsigned NOT NULL,
  `submit_date` datetime DEFAULT NULL,
  `status` tinyint(4) DEFAULT NULL,
  `file_url` varchar(255) DEFAULT NULL,
  `file_size` int(11) DEFAULT NULL,
  `score` float NOT NULL DEFAULT '-1',
  `review` text,
  `tutid` int(11) DEFAULT NULL,
  `tname` varchar(45) DEFAULT NULL,
  `view` int(1) DEFAULT '0',
  `rank` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`srid`),
  UNIQUE KEY `submit_uni` (`aid`,`sid`),
  KEY `fk_assign_submit_record_assignment1_idx` (`aid`,`lid`),
  KEY `srid_idx` (`srid`),
  KEY `sid` (`sid`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=103 ;

--
-- 转存表中的数据 `assign_submit_record`
--

INSERT INTO `assign_submit_record` (`srid`, `sid`, `aid`, `lid`, `submit_date`, `status`, `file_url`, `file_size`, `score`, `review`, `tutid`, `tname`, `view`, `rank`) VALUES
(44, 5003, 8, 14, '2014-09-02 10:37:48', 0, '8/5003.zip', 0, 90, '', 6001, '六一', 1, 0),
(45, 6003, 8, 14, '2014-09-05 15:23:29', 1, NULL, 0, 45, '', 6001, '六一', 1, 0),
(46, 6004, 8, 14, '2014-09-05 15:23:29', 1, NULL, 0, 0, '', 6001, '六一', 1, 0),
(47, 5005, 9, 8, '2014-09-02 10:38:58', 0, '9/5005.pdf', 0, 0, NULL, 0, NULL, 0, 0),
(48, 5006, 9, 8, '2014-09-05 15:29:44', 1, NULL, 0, 0, NULL, 0, NULL, 0, 0),
(49, 5008, 9, 8, '2014-09-05 15:29:44', 1, NULL, 0, 0, NULL, 0, NULL, 0, 0),
(50, 5009, 9, 8, '2014-09-05 15:29:44', 1, NULL, 0, 0, NULL, 0, NULL, 0, 0),
(51, 5010, 9, 8, '2014-09-05 15:29:44', 1, NULL, 0, 0, NULL, 0, NULL, 0, 0),
(52, 5005, 10, 12, '2014-09-02 10:22:05', 1, NULL, 0, 0, NULL, 0, NULL, 0, 0),
(53, 5006, 10, 12, '2014-09-02 10:22:05', 1, NULL, 0, 0, NULL, 0, NULL, 0, 0),
(54, 5008, 10, 12, '2014-09-02 10:42:59', 0, '10/5008.pdf', 0, 0, NULL, 0, NULL, 0, 0),
(55, 5009, 10, 12, '2014-09-02 10:22:05', 1, NULL, 0, 0, NULL, 0, NULL, 0, 0),
(56, 5010, 10, 12, '2014-09-02 10:22:05', 1, NULL, 0, 0, NULL, 0, NULL, 0, 0),
(57, 5005, 11, 10, '2014-09-02 10:39:50', 0, '11/5005.rar', 0, 0, NULL, 0, NULL, 0, 0),
(58, 5006, 11, 10, '2014-09-02 10:24:23', 1, NULL, 0, 0, NULL, 0, NULL, 0, 0),
(59, 5007, 11, 10, '2014-09-02 10:24:23', 1, NULL, 0, 0, NULL, 0, NULL, 0, 0),
(60, 5005, 12, 11, '2014-09-02 10:41:08', 0, '12/5005.pdf', 0, 0, NULL, 6001, '六一', 1, 0),
(61, 5006, 12, 11, '2014-09-02 10:26:02', 0, '12/5006.pdf', 0, 0, '', 6001, '六一', 1, 0),
(62, 5008, 12, 11, '2014-09-02 10:42:25', 0, '12/5008.pdf', 0, 0, NULL, 6001, '六一', 1, 0),
(63, 5005, 13, 11, '2014-09-02 10:26:02', 1, NULL, 0, 0, '', 6001, '六一', 1, 0),
(64, 5009, 12, 11, '2014-09-02 10:26:02', 1, NULL, 0, 0, NULL, 0, NULL, 0, 0),
(65, 5006, 13, 11, '2014-09-02 10:26:02', 1, NULL, 0, 30, '一般', 6001, '六一', 1, 0),
(66, 5008, 13, 11, '2014-09-02 10:26:02', 1, NULL, 0, 0, NULL, 0, NULL, 0, 0),
(67, 5010, 12, 11, '2014-09-02 10:26:02', 1, NULL, 0, 0, NULL, 0, NULL, 0, 0),
(68, 5009, 13, 11, '2014-09-02 10:26:02', 1, NULL, 0, 0, NULL, 0, NULL, 0, 0),
(69, 5010, 13, 11, '2014-09-02 10:26:02', 1, NULL, 0, 0, NULL, 0, NULL, 0, 0),
(70, 5003, 14, 15, '2015-03-28 21:31:52', 1, NULL, 0, 0, NULL, 0, NULL, 0, 0),
(71, 6003, 14, 15, '2015-03-28 21:31:52', 1, NULL, 0, 0, NULL, 0, NULL, 0, 0),
(72, 6004, 14, 15, '2015-03-28 21:31:52', 1, NULL, 0, 0, NULL, 0, NULL, 0, 0),
(73, 5005, 15, 25, '2015-03-28 21:37:38', 1, NULL, 0, 0, NULL, 0, NULL, 0, 0),
(74, 5006, 15, 25, '2015-03-28 21:37:38', 0, '16/5006.doc', 0, 50, NULL, 6001, NULL, 1, 0),
(75, 5008, 15, 25, '2015-03-28 21:37:38', 0, '16/5008doc', 0, 37, NULL, 6001, NULL, 1, 0),
(76, 5009, 15, 25, '2015-03-28 21:37:39', 0, '16/5009.doc', 0, 28, NULL, 6001, NULL, 1, 0),
(77, 5010, 15, 25, '2015-03-28 21:37:39', 0, '16/50010.doc', 0, 40, NULL, 6001, NULL, 1, 0),
(78, 5005, 16, 24, '2015-03-28 21:39:04', 0, '16/5005.doc', 0, 4, NULL, 0, NULL, 0, 3),
(79, 5006, 16, 24, '2015-03-28 21:39:04', 0, '16/5006.doc', 0, 5, NULL, 0, NULL, 0, 1),
(80, 5008, 16, 24, '2015-03-26 21:39:04', 0, '16/5008.doc', 0, 3, NULL, 0, NULL, 0, 4),
(81, 5009, 16, 24, '2015-03-28 21:39:04', 0, '16/5009.doc', 0, 4, NULL, 0, NULL, 0, 2),
(82, 5010, 16, 24, '2015-03-27 21:39:04', 0, '16/50010.doc', 0, 2, NULL, 0, NULL, 0, 5),
(83, 5005, 17, 24, '2015-03-28 21:40:02', 1, NULL, 0, 1, 'good', 5001, '王一', 1, 0),
(84, 5006, 17, 24, '2015-03-27 21:40:02', 0, '17/5006.doc', 0, 4, NULL, 6001, '六一', 1, 0),
(85, 5008, 17, 24, '2015-03-26 21:40:02', 0, '17/5008.doc', 0, 5, NULL, 6001, '六一', 1, 0),
(86, 5009, 17, 24, '2015-03-28 21:40:02', 0, '17/5009.doc', 0, 5, NULL, 6001, '六一', 1, 0),
(87, 5010, 17, 24, '2015-03-26 21:40:02', 0, '17/5010.doc', 0, 3, NULL, 6001, '六一', 1, 0),
(88, 5005, 18, 24, '2015-04-01 00:29:01', 1, NULL, 0, 0, NULL, 0, NULL, 0, 0),
(89, 5006, 18, 24, '2015-04-01 00:31:08', 0, '18/5006.pdf', 0, 0, NULL, 0, NULL, 0, 0),
(90, 5008, 18, 24, '2015-04-01 00:29:01', 1, NULL, 0, 0, NULL, 0, NULL, 0, 0),
(91, 5009, 18, 24, '2015-04-01 00:29:01', 1, NULL, 0, 0, NULL, 0, NULL, 0, 0),
(92, 5010, 18, 24, '2015-04-01 00:29:01', 1, NULL, 0, 0, NULL, 0, NULL, 0, 0),
(93, 5005, 19, 24, '2015-04-01 11:09:39', 1, NULL, 0, 0, NULL, 0, NULL, 0, 0),
(94, 5006, 19, 24, '2015-04-01 11:11:52', 0, '19/5006.doc', 0, 0, NULL, 0, NULL, 0, 0),
(95, 5008, 19, 24, '2015-04-01 11:09:40', 1, NULL, 0, 0, NULL, 0, NULL, 0, 0),
(96, 5009, 19, 24, '2015-04-01 11:09:40', 1, NULL, 0, 0, NULL, 0, NULL, 0, 0),
(97, 5010, 19, 24, '2015-04-01 11:09:40', 1, NULL, 0, 0, NULL, 0, NULL, 0, 0),
(98, 5005, 20, 27, '2015-06-14 20:56:40', 1, NULL, 0, 0, NULL, 0, NULL, 0, 0),
(99, 5006, 20, 27, '2015-06-14 20:56:40', 1, NULL, 0, 0, NULL, 0, NULL, 0, 0),
(100, 5008, 20, 27, '2015-06-14 20:56:40', 1, NULL, 0, 0, NULL, 0, NULL, 0, 0),
(101, 5009, 20, 27, '2015-06-14 20:56:40', 1, NULL, 0, 0, NULL, 0, NULL, 0, 0),
(102, 5010, 20, 27, '2015-06-14 20:56:40', 1, NULL, 0, 0, NULL, 0, NULL, 0, 0);

-- --------------------------------------------------------

--
-- 表的结构 `comment`
--

CREATE TABLE IF NOT EXISTS `comment` (
  `comid` int(11) NOT NULL AUTO_INCREMENT,
  `aid` int(10) unsigned DEFAULT NULL,
  `timestamp` datetime DEFAULT NULL,
  `conntent` varchar(255) DEFAULT NULL,
  `sid` int(10) DEFAULT '-1',
  `toSid` int(10) DEFAULT '-1',
  `toName` varchar(255) DEFAULT NULL,
  `fromName` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`comid`),
  KEY `aid_idx` (`aid`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=7 ;

--
-- 转存表中的数据 `comment`
--

INSERT INTO `comment` (`comid`, `aid`, `timestamp`, `conntent`, `sid`, `toSid`, `toName`, `fromName`) VALUES
(1, 19, '2015-06-10 21:42:23', '请问怎样做', 5001, 12, 'abc', '王一'),
(2, 19, '2015-06-10 23:28:04', '按照要求即可', 0, 12, NULL, '李二'),
(3, 19, '2015-06-10 23:43:55', '可自由发挥', 0, 12, NULL, '曾生'),
(4, 19, '2015-06-10 23:44:11', '好的', 0, 12, NULL, '王一'),
(5, 19, '2015-06-16 14:37:07', '下周一提交课堂报告PPT', 1007, 12, 'abc', '曾生'),
(6, 19, '2015-06-16 15:04:38', '烫烫烫', 5006, 12, 'abc', '陈六');

-- --------------------------------------------------------

--
-- 表的结构 `course`
--

CREATE TABLE IF NOT EXISTS `course` (
  `cid` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `institute` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`cid`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=10000 ;

--
-- 转存表中的数据 `course`
--

INSERT INTO `course` (`cid`, `name`, `institute`) VALUES
(1, '计算机网络', '软件学院'),
(2, '软件工程1', '软件学院'),
(3, '软件工程2', '软件学院'),
(4, '软件工程3', '软件学院'),
(5, '商务智能', '软件学院'),
(6, '面向服务的软件工程', '软件学院'),
(7, '计算机组织与结构', '软件学院'),
(8, '软件体系结构与设计', '软件学院'),
(9, '软件测试', '数学学院'),
(10, '编译原理', '软件学院'),
(11, '嵌入式系统概论', '软件学院'),
(12, '软件构造', '软件学院'),
(13, '需求工程', '数学院'),
(14, '数据库开发技术', '软件学院'),
(15, 'Linux系统基础', '软件学院'),
(18, '数据结构', '软件学院'),
(19, 'C++程序设计', '软件学院'),
(20, '操作系统', '软件学院'),
(21, 'Linux程序设计', '软件学院'),
(22, 'web', '软件学院'),
(111, '软件工程', '软件学院'),
(9999, 'JAVAEE', '软件学院');

-- --------------------------------------------------------

--
-- 表的结构 `goal`
--

CREATE TABLE IF NOT EXISTS `goal` (
  `gid` int(11) NOT NULL AUTO_INCREMENT,
  `content` text,
  `year` date DEFAULT NULL,
  PRIMARY KEY (`gid`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=4 ;

--
-- 转存表中的数据 `goal`
--

INSERT INTO `goal` (`gid`, `content`, `year`) VALUES
(1, '进入21世纪以来，我国高等教育经历了“跨越式”的发展。信息化时代的到来改变了人们的传统学习方式，学习过程中的个性化需求越来越明显，中国大学生普遍缺乏批判性思维，“钱学森之问”成为中国教育界共同面临的“难题”', '2012-09-01'),
(2, '<div>​eeeeeeeeeeeee</div>', '2014-06-16'),
(3, '<div>​战略计划</div>', '2015-06-16');

-- --------------------------------------------------------

--
-- 表的结构 `hibernate_sequence`
--

CREATE TABLE IF NOT EXISTS `hibernate_sequence` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- 转存表中的数据 `hibernate_sequence`
--

INSERT INTO `hibernate_sequence` (`next_val`) VALUES
(2);

-- --------------------------------------------------------

--
-- 表的结构 `lesson`
--

CREATE TABLE IF NOT EXISTS `lesson` (
  `lid` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `term` varchar(45) NOT NULL,
  `end_date` date DEFAULT NULL,
  `status` int(4) DEFAULT '0',
  `no` tinyint(3) unsigned DEFAULT NULL,
  `cid` int(10) unsigned NOT NULL,
  `start_date` date DEFAULT NULL,
  `term_id` int(11) DEFAULT NULL,
  `score_description` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`lid`),
  UNIQUE KEY `lesson_uni` (`cid`,`term`,`no`),
  KEY `fk_lesson_course1_idx` (`cid`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=29 ;

--
-- 转存表中的数据 `lesson`
--

INSERT INTO `lesson` (`lid`, `term`, `end_date`, `status`, `no`, `cid`, `start_date`, `term_id`, `score_description`) VALUES
(8, '2014-2014学年第1学期', '2014-11-30', 0, 1, 6, '2014-09-02', 6, NULL),
(9, '2014-2014学年第1学期', '2014-11-30', 0, 1, 10, '2014-09-02', 6, NULL),
(10, '2014-2014学年第1学期', '2014-11-30', 0, 1, 11, '2014-09-02', 6, NULL),
(11, '2014-2014学年第1学期', '2014-11-30', 0, 1, 12, '2014-09-02', 6, NULL),
(12, '2014-2014学年第1学期', '2014-11-30', 0, 1, 13, '2014-09-02', 6, NULL),
(13, '2014-2014学年第1学期', '2014-11-30', 0, 1, 14, '2014-09-02', 6, NULL),
(14, '2014-2014学年第1学期', '2014-11-30', 0, 1, 3, '2014-09-02', 6, NULL),
(15, '2014-2015学年第2学期', '2015-04-10', 0, 1, 2, '2014-12-01', 7, NULL),
(16, '2014-2015学年第2学期', '2015-04-10', 0, 1, 3, '2014-12-01', 7, NULL),
(17, '2014-2015学年第2学期', '2015-04-10', 0, 1, 4, '2014-12-01', 7, NULL),
(23, '2014-2015学年第2学期', '2015-04-10', 0, 1, 9999, '2014-11-30', 7, NULL),
(24, '2014-2015学年第2学期', '2015-04-10', 0, 1, 15, '2014-11-30', 7, '作业成绩 * 40% + 考试成绩 * 60%'),
(25, '2014-2015学年第2学期', '2015-04-10', 0, 1, 8, '2014-11-30', 7, NULL),
(27, '2014-2015学年第3学期', '2015-08-28', 0, 1, 4, '2015-04-11', 8, NULL),
(28, '2014-2015学年第3学期', '2015-08-28', 0, 1, 8, '2015-04-11', 8, NULL);

-- --------------------------------------------------------

--
-- 替换视图以便查看 `lessonsummary`
--
CREATE TABLE IF NOT EXISTS `lessonsummary` (
`lid` int(10) unsigned
,`term` varchar(45)
,`lessonname` varchar(255)
,`institute` varchar(45)
,`maxscore` float
,`stunum` bigint(21)
,`avgscore` double
,`stddev` double
,`perfect` decimal(23,0)
,`great` decimal(23,0)
,`good` decimal(23,0)
,`bad` decimal(23,0)
,`badrate` decimal(27,4)
);
-- --------------------------------------------------------

--
-- 表的结构 `lesson_record`
--

CREATE TABLE IF NOT EXISTS `lesson_record` (
  `rid` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `sid` int(10) unsigned NOT NULL,
  `lid` int(10) unsigned NOT NULL,
  `course_name` varchar(45) DEFAULT NULL,
  `score` float DEFAULT '0',
  `rank` int(10) NOT NULL DEFAULT '-1',
  `homework_score` float NOT NULL DEFAULT '0',
  `exam_score` float NOT NULL DEFAULT '0',
  PRIMARY KEY (`rid`),
  UNIQUE KEY `lesson_record_uni` (`sid`,`lid`),
  KEY `fk_student_has_lesson_lesson2_idx` (`lid`),
  KEY `fk_student_has_lesson_student1_idx` (`sid`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=91 ;

--
-- 转存表中的数据 `lesson_record`
--

INSERT INTO `lesson_record` (`rid`, `sid`, `lid`, `course_name`, `score`, `rank`, `homework_score`, `exam_score`) VALUES
(39, 5003, 14, NULL, 60, 14, 0, 0),
(40, 6003, 14, NULL, 78, 8, 0, 0),
(41, 6004, 14, NULL, 25, 21, 0, 0),
(42, 5005, 8, NULL, 45, 19, 0, 0),
(43, 5006, 8, NULL, 67, 11, 0, 0),
(44, 5008, 8, NULL, 23, 22, 0, 0),
(45, 5009, 8, NULL, 45, 19, 0, 0),
(46, 5010, 8, NULL, 78, 8, 0, 0),
(47, 5005, 12, NULL, 99, 1, 0, 0),
(48, 5006, 12, NULL, 0, 0, 0, 0),
(49, 5008, 12, NULL, 56, 16, 0, 0),
(50, 5009, 12, NULL, 45, 19, 0, 0),
(51, 5010, 12, NULL, 34, 20, 0, 0),
(52, 5011, 9, NULL, 0, 0, 0, 0),
(53, 5010, 9, NULL, 88, 4, 0, 0),
(54, 5012, 9, NULL, 56, 16, 0, 0),
(55, 5005, 10, NULL, 34, 20, 0, 0),
(56, 5006, 10, NULL, 98, 2, 0, 0),
(57, 5007, 10, NULL, 65, 13, 0, 0),
(58, 5008, 13, NULL, 57, 15, 0, 0),
(59, 5009, 13, NULL, 67, 12, 0, 0),
(60, 5005, 11, NULL, -1, 0, 0, 0),
(61, 5006, 11, NULL, -1, 0, 0, 0),
(62, 5008, 11, NULL, -1, 0, 0, 0),
(63, 5009, 11, NULL, -1, 0, 0, 0),
(64, 5010, 11, NULL, -1, 0, 0, 0),
(65, 5003, 15, NULL, 65, 13, 0, 0),
(66, 6003, 15, NULL, 45, 19, 0, 0),
(67, 6004, 15, NULL, 45, 19, 0, 0),
(68, 5011, 16, NULL, 90, 3, 0, 0),
(69, 5012, 16, NULL, 77, 9, 0, 0),
(70, 5013, 16, NULL, 54, 18, 0, 0),
(71, 5014, 16, NULL, 76, 10, 0, 0),
(72, 5005, 25, NULL, 98, 2, 0, 0),
(73, 5006, 25, NULL, 88, 5, 0, 0),
(74, 5008, 25, NULL, 86, 6, 0, 0),
(75, 5009, 25, NULL, 86, 7, 0, 0),
(76, 5010, 25, NULL, 45, 19, 0, 0),
(77, 5005, 23, NULL, 3, 25, 0, 0),
(78, 5006, 23, NULL, 3, 26, 0, 0),
(79, 6003, 17, NULL, 7, 23, 0, 0),
(80, 6004, 17, NULL, 5, 24, 0, 0),
(81, 5005, 24, NULL, 95, 1, 5, 90),
(82, 5006, 24, NULL, 64, 2, 9, 58),
(83, 5008, 24, NULL, 54, 3, 8, 46),
(84, 5009, 24, NULL, 23, 5, 9, 14),
(85, 5010, 24, NULL, 34, 4, 5, 29),
(86, 5006, 27, NULL, 67, 12, 0, 0),
(87, 5009, 27, NULL, 53, 19, 0, 0),
(88, 5005, 27, NULL, 56, 17, 0, 0),
(89, 5008, 27, NULL, 54, 18, 0, 0),
(90, 5010, 27, NULL, -1, 0, 0, 0);

-- --------------------------------------------------------

--
-- 表的结构 `student`
--

CREATE TABLE IF NOT EXISTS `student` (
  `sid` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  `password` varchar(45) DEFAULT NULL,
  `admission_year` date DEFAULT NULL,
  `institute` varchar(45) DEFAULT NULL,
  `is_tuitor` bit(1) DEFAULT NULL,
  PRIMARY KEY (`sid`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=6005 ;

--
-- 转存表中的数据 `student`
--

INSERT INTO `student` (`sid`, `name`, `password`, `admission_year`, `institute`, `is_tuitor`) VALUES
(12, NULL, '12', '2015-01-01', NULL, b'0'),
(1099, 'jj', '1099', '2015-01-01', '电子系', b'1'),
(1999, 'f', '1999', '2015-01-01', '电子系', b'1'),
(5001, '王一', '5001', '2012-01-01', '电子系', b'1'),
(5002, '李二', '5002', '2012-01-01', '电子系', b'1'),
(5003, '张三', '5003', '2013-01-01', '软件学院', b'0'),
(5004, '李四', '5004', '2013-01-01', '数学系', b'0'),
(5005, '欧五', '5005', '2012-01-01', '软件学院', b'1'),
(5006, '陈六', '5006', '2012-01-01', '软件学院', b'0'),
(5007, '韩七', '5007', '2014-01-01', '物理学院', b'0'),
(5008, '李淳雨', '5008', '2012-01-01', '软件学院', b'0'),
(5009, '蒋秉良', '5009', '2012-01-01', '软件学院', b'0'),
(5010, '李十', '5010', '2012-01-01', '软件学院', b'0'),
(5011, '王十一', '5011', '2015-01-01', '软件学院', b'0'),
(5012, '李十二', '5012', '2015-01-01', '软件学院', b'0'),
(5013, '李十三', '5013', '2015-01-01', '软件学院', b'0'),
(5014, '李十四', '5014', '2015-01-01', '软件学院', b'0'),
(5679, '445', '5679', '2007-01-01', '电子系', b'1'),
(6001, '六一', '6001', '2011-01-01', '软件学院', b'1'),
(6002, '六二', '6002', '2011-01-01', '软件学院', b'1'),
(6003, '六三', '6003', '2013-01-01', '软件学院', b'0'),
(6004, '六四', '6004', '2013-01-01', '软件学院', b'0');

-- --------------------------------------------------------

--
-- 替换视图以便查看 `studentscoresummary`
--
CREATE TABLE IF NOT EXISTS `studentscoresummary` (
`rid` int(10) unsigned
,`sid` int(10) unsigned
,`studentname` varchar(45)
,`institute` varchar(45)
,`term` varchar(45)
,`lid` int(10) unsigned
,`coursename` varchar(255)
,`rank` int(10)
,`homeworkscore` float
,`examscore` float
,`score` float
);
-- --------------------------------------------------------

--
-- 表的结构 `teacher`
--

CREATE TABLE IF NOT EXISTS `teacher` (
  `tid` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  `password` varchar(45) DEFAULT NULL,
  `is_manager` tinyint(1) DEFAULT NULL,
  `institute` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`tid`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=1013 ;

--
-- 转存表中的数据 `teacher`
--

INSERT INTO `teacher` (`tid`, `name`, `password`, `is_manager`, `institute`) VALUES
(1001, '陈道蓄', '1001', 1, '软件学院'),
(1002, '刘钦', '1002', 0, '软件学院'),
(1003, '贝佳', '1003', 1, '软件学院'),
(1004, '刘峰', '1004', 0, '软件学院'),
(1005, '丁二玉', '1005', 0, '软件学院'),
(1006, '宋鸿兵', '1006', 0, '数学系'),
(1007, '曾生', '1007', 0, '软件学院'),
(1008, '刘海涛', '1008', 0, '软件学院'),
(1009, '李翔', '1009', 0, '软件学院'),
(1010, '刘嘉', '1010', 0, '软件学院'),
(1011, '郑涛', '1011', 0, '软件学院'),
(1012, '王浩然', '1012', 1, '软件学院');

-- --------------------------------------------------------

--
-- 表的结构 `teacher_assign`
--

CREATE TABLE IF NOT EXISTS `teacher_assign` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `tid` int(10) unsigned NOT NULL,
  `lid` int(10) unsigned NOT NULL,
  `course_name` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `lesson_uni` (`tid`,`lid`),
  KEY `fk_teacher_has_lesson_lesson1_idx` (`lid`),
  KEY `fk_teacher_has_lesson_teacher1_idx` (`tid`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=37 ;

--
-- 转存表中的数据 `teacher_assign`
--

INSERT INTO `teacher_assign` (`id`, `tid`, `lid`, `course_name`) VALUES
(20, 1003, 8, NULL),
(21, 1006, 9, NULL),
(23, 1008, 10, NULL),
(24, 1009, 11, NULL),
(25, 1005, 12, NULL),
(26, 1010, 13, NULL),
(27, 1005, 14, NULL),
(28, 1002, 14, NULL),
(29, 1002, 15, NULL),
(30, 1005, 16, NULL),
(31, 1002, 16, NULL),
(32, 1010, 17, NULL),
(33, 1012, 23, NULL),
(34, 1007, 24, NULL),
(35, 1005, 25, NULL),
(36, 1007, 27, NULL);

-- --------------------------------------------------------

--
-- 表的结构 `term`
--

CREATE TABLE IF NOT EXISTS `term` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `start_date` date DEFAULT NULL,
  `end_date` date DEFAULT NULL,
  `no` tinyint(4) DEFAULT '1',
  `year` int(4) DEFAULT NULL,
  `status` int(4) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=10 ;

--
-- 转存表中的数据 `term`
--

INSERT INTO `term` (`id`, `start_date`, `end_date`, `no`, `year`, `status`) VALUES
(6, '2014-09-02', '2014-11-30', 1, 2014, 2),
(7, '2014-12-01', '2015-04-10', 2, 2014, 2),
(8, '2015-04-11', '2015-08-28', 3, 2014, 0),
(9, '2015-08-29', '2016-01-31', 4, 2014, 0);

-- --------------------------------------------------------

--
-- 表的结构 `tuitor_assign`
--

CREATE TABLE IF NOT EXISTS `tuitor_assign` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `student_sid` int(10) unsigned NOT NULL,
  `lesson_lid` int(10) unsigned NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `tuitor_uni` (`student_sid`,`lesson_lid`),
  KEY `fk_tuitor_assign_lesson1_idx` (`lesson_lid`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=23 ;

--
-- 转存表中的数据 `tuitor_assign`
--

INSERT INTO `tuitor_assign` (`id`, `student_sid`, `lesson_lid`) VALUES
(8, 5001, 9),
(11, 5001, 10),
(20, 5001, 24),
(6, 5002, 8),
(10, 5002, 10),
(9, 5005, 10),
(12, 6001, 10),
(15, 6001, 11),
(7, 6001, 12),
(5, 6001, 14),
(19, 6001, 17),
(22, 6001, 27),
(13, 6002, 10),
(14, 6002, 13),
(16, 6002, 15),
(18, 6002, 23),
(17, 6002, 25),
(21, 6002, 27);

-- --------------------------------------------------------

--
-- 视图结构 `assignmentsummary`
--
DROP TABLE IF EXISTS `assignmentsummary`;

CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `assignmentsummary` AS select `ass`.`name` AS `name`,`asr`.`lid` AS `lid`,`asr`.`aid` AS `aid`,count(0) AS `stunum`,`ass`.`score` AS `aiisgnmentscore`,max(`asr`.`score`) AS `maxscore`,avg(`asr`.`score`) AS `avgscore`,(avg(`asr`.`score`) / `ass`.`score`) AS `avgrate`,sum((case when (((`asr`.`score` / `ass`.`score`) > 0.9) and ((`asr`.`score` / `ass`.`score`) <= 1)) then 1 else 0 end)) AS `perfect`,sum((case when (((`asr`.`score` / `ass`.`score`) > 0.7) and ((`asr`.`score` / `ass`.`score`) <= 0.9)) then 1 else 0 end)) AS `greate`,sum((case when (((`asr`.`score` / `ass`.`score`) > 0.60) and ((`asr`.`score` / `ass`.`score`) <= 0.70)) then 1 else 0 end)) AS `good`,sum((case when (((`asr`.`score` / `ass`.`score`) < 0.60) and (`asr`.`status` = 0)) then 1 else 0 end)) AS `bad`,sum((case when (`asr`.`status` = 1) then 1 else 0 end)) AS `miss` from (`assign_submit_record` `asr` join `assignment` `ass`) where (`ass`.`aid` = `asr`.`aid`) group by `asr`.`aid`;

-- --------------------------------------------------------

--
-- 视图结构 `lessonsummary`
--
DROP TABLE IF EXISTS `lessonsummary`;

CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `lessonsummary` AS select `asr`.`lid` AS `lid`,`l`.`term` AS `term`,`c`.`name` AS `lessonname`,`c`.`institute` AS `institute`,max(`asr`.`score`) AS `maxscore`,count(0) AS `stunum`,avg(`asr`.`score`) AS `avgscore`,std(`asr`.`score`) AS `stddev`,sum((case when ((`asr`.`score` > 90) and (`asr`.`score` <= 100)) then 1 else 0 end)) AS `perfect`,sum((case when ((`asr`.`score` > 70) and (`asr`.`score` <= 90)) then 1 else 0 end)) AS `great`,sum((case when ((`asr`.`score` > 60) and (`asr`.`score` <= 70)) then 1 else 0 end)) AS `good`,sum((case when (`asr`.`score` < 60) then 1 else 0 end)) AS `bad`,(sum((case when (`asr`.`score` < 60) then 1 else 0 end)) / count(0)) AS `badrate` from ((`lesson_record` `asr` join `lesson` `l`) join `course` `c`) where ((`c`.`cid` = `l`.`cid`) and (`l`.`lid` = `asr`.`lid`)) group by `asr`.`lid`;

-- --------------------------------------------------------

--
-- 视图结构 `studentscoresummary`
--
DROP TABLE IF EXISTS `studentscoresummary`;

CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `studentscoresummary` AS select `r`.`rid` AS `rid`,`s`.`sid` AS `sid`,`s`.`name` AS `studentname`,`s`.`institute` AS `institute`,`l`.`term` AS `term`,`l`.`lid` AS `lid`,`c`.`name` AS `coursename`,`r`.`rank` AS `rank`,`r`.`homework_score` AS `homeworkscore`,`r`.`exam_score` AS `examscore`,`r`.`score` AS `score` from (((`lesson` `l` join `student` `s`) join `course` `c`) join `lesson_record` `r`) where ((`l`.`cid` = `c`.`cid`) and (`l`.`lid` = `r`.`lid`) and (`r`.`sid` = `s`.`sid`));

--
-- 限制导出的表
--

--
-- 限制表 `assignment`
--
ALTER TABLE `assignment`
  ADD CONSTRAINT `fk_assignment_lesson1` FOREIGN KEY (`lid`) REFERENCES `lesson` (`lid`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- 限制表 `assign_submit_record`
--
ALTER TABLE `assign_submit_record`
  ADD CONSTRAINT `fk_assign_submit_record_assignment1` FOREIGN KEY (`aid`) REFERENCES `assignment` (`aid`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `sid` FOREIGN KEY (`sid`) REFERENCES `student` (`sid`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- 限制表 `comment`
--
ALTER TABLE `comment`
  ADD CONSTRAINT `aid` FOREIGN KEY (`aid`) REFERENCES `assignment` (`aid`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- 限制表 `lesson`
--
ALTER TABLE `lesson`
  ADD CONSTRAINT `fk_lesson_course1` FOREIGN KEY (`cid`) REFERENCES `course` (`cid`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- 限制表 `lesson_record`
--
ALTER TABLE `lesson_record`
  ADD CONSTRAINT `fk_student_has_lesson_lesson2` FOREIGN KEY (`lid`) REFERENCES `lesson` (`lid`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_student_has_lesson_student1` FOREIGN KEY (`sid`) REFERENCES `student` (`sid`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- 限制表 `teacher_assign`
--
ALTER TABLE `teacher_assign`
  ADD CONSTRAINT `fk_teacher_has_lesson_lesson1` FOREIGN KEY (`lid`) REFERENCES `lesson` (`lid`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_teacher_has_lesson_teacher1` FOREIGN KEY (`tid`) REFERENCES `teacher` (`tid`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- 限制表 `tuitor_assign`
--
ALTER TABLE `tuitor_assign`
  ADD CONSTRAINT `fk_tuitor_assign_lesson1` FOREIGN KEY (`lesson_lid`) REFERENCES `lesson` (`lid`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_tuitor_assign_student1` FOREIGN KEY (`student_sid`) REFERENCES `student` (`sid`) ON DELETE NO ACTION ON UPDATE NO ACTION;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
