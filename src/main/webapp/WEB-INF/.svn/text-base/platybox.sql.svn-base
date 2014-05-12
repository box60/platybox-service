
SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `platybox`
--

-- to add stuff to our database -- 
-- ALTER IGNORE TABLE  `users_scores` ADD `coins` int(11) NOT NULL DEFAULT 0 AFTER `experience`;
-- ALTER TABLE `users_scores` ADD `coins` int(11) NOT NULL DEFAULT 0 AFTER `experience`; --

-- --------------------------------------------------------

--
-- House Keeping procedures. 
--

--ALTER IGNORE TABLE  `users_scores` CHANGE  `experience`  `promos` INT( 11 ) NOT NULL DEFAULT  '0';
--ALTER IGNORE TABLE `users_scores` ADD `quests` INT( 11 ) NOT NULL DEFAULT  '0' AFTER `promos`;


--
-- Cleaning deprecated tables after a major upgrade 
--

DROP TABLE IF EXISTS checkins_places;
ALTER TABLE  `bits_registrations` DROP  `places_id` ;
ALTER TABLE  `bits_scanned` DROP  `places_id`;
ALTER TABLE  `phones_statuses` DROP  `places_id` ;


-- -------------------------------------------------------

CREATE TABLE IF NOT EXISTS `bits` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `bits_types_id` int(11) NOT NULL DEFAULT '7',
  `name` varchar(255) NOT NULL DEFAULT 'A bit of life.',
  `description` varchar(255) NOT NULL DEFAULT 'A small part of life, something amazing that you love.',
  `qr_image_url` varchar(255) NOT NULL DEFAULT '',
  `places_id` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=251 ;

ALTER TABLE bits CHANGE id id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT;
ALTER TABLE bits CHANGE places_id places_id BIGINT UNSIGNED NOT NULL DEFAULT '1';

INSERT IGNORE INTO `bits` (`id`, `bits_types_id`, `name`, `qr_image_url` ,`places_id`) VALUES
(1, 3, 'A bit of life.', 'http://platybox.com', 1),
(2, 2, 'Utopia. Nowhere', 'http://img.platybox.com/qr/2.gif	', 1),
(5, 2, 'Box 60 Inc.', 'http://img.platybox.com/qr/5.gif	', 2);


CREATE TABLE IF NOT EXISTS `bits_types` (
  `id` int(11) NOT NULL AUTO_INCREMENT,  
  `type` varchar(255) NOT NULL DEFAULT '',  
  PRIMARY KEY (`id`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=10;


INSERT IGNORE INTO `bits_types` (`id`, `type`) VALUES
(1, 'bits'),
(2, 'places'),
(3, 'tables'),
(4, 'drinks'),
(5, 'foods'),
(6, 'displays'),
(7, 'contents'),
(8, 'users'),
(9, 'promos');


CREATE TABLE IF NOT EXISTS `places` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `description` varchar(255) NOT NULL DEFAULT '',
  `address` varchar(255) NOT NULL DEFAULT '',
  `postal` varchar(255) NOT NULL DEFAULT '',
  `city` varchar(255) NOT NULL DEFAULT '',
  `province` varchar(255) NOT NULL DEFAULT '',
  `country` varchar(255) NOT NULL DEFAULT '',
  `phone` varchar(255) NOT NULL DEFAULT '',
  `geolat` double NOT NULL DEFAULT 49.263495,
  `geolong` double NOT NULL DEFAULT -123.184674,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=4 ;

ALTER TABLE places CHANGE id id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT;
ALTER TABLE places CHANGE description description LONGTEXT NOT NULL DEFAULT '';
ALTER IGNORE TABLE  places ADD timezone varchar(255) NOT NULL DEFAULT 'America/Vancouver' AFTER `phone`;


INSERT IGNORE INTO `places` (`id`, `name`, `description`, `address`, `postal`, `city`, `province`, `country`, `phone`, `geolat`, `geolong`) VALUES
(1, 'Utopia, Nowhere', 'Where you find nothing', '1 Nowhere Rd.', '000000', 'Nowhere', 'NL', 'Neverland', '"+1 604-315-1819',49.263495, -123.184674),
(2, 'Box 60 Inc.', 'The office of the creators of Platybox.', '3539 W.10th Ave.', 'V6R2E9', 'Vancouver', 'BC', 'Canada', '"+1 778-996-7359', 49.263495, -123.184674),
(3, 'The Platypus Cafe.','The coffee shop that only serves platypuses.', '3539 W.10th Ave.', 'V6R2E9', 'Vancouver', 'BC', 'Canada', '"+1 778-996-7359', 49.263495, -123.184674);


CREATE TABLE IF NOT EXISTS `accessors` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `users_id` int(11) NOT NULL DEFAULT '0',
  `consumers_id` int(11) NOT NULL DEFAULT '0',
  `access_token` varchar(255) NOT NULL DEFAULT '',
  `request_token` varchar(255) NOT NULL DEFAULT '',
  `token_secret` varchar(255) NOT NULL DEFAULT '',
  `callback_url` varchar(255) NOT NULL DEFAULT '',
  `authorized` tinyint(4) NOT NULL DEFAULT '0',
  `timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`, `consumers_id`,`access_token`,`request_token`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1 AUTO_INCREMENT=1;

ALTER TABLE accessors CHANGE id id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT;
ALTER TABLE accessors CHANGE users_id users_id BIGINT UNSIGNED NOT NULL DEFAULT 0;
ALTER TABLE accessors CHANGE consumers_id consumers_id BIGINT UNSIGNED NOT NULL DEFAULT 0;

CREATE TABLE IF NOT EXISTS `consumers` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `users_id` int(11) NOT NULL,
  `consumers_levels_id` int(11) NOT NULL DEFAULT 5,
  `consumer_key` varchar(255) NOT NULL,
  `consumer_secret` varchar(255) NOT NULL,
  `callback_url` varchar(255) NOT NULL DEFAULT '',
  `timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `key` (`consumer_key`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=3;

ALTER TABLE consumers CHANGE id id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT;
ALTER TABLE consumers CHANGE users_id users_id BIGINT UNSIGNED NOT NULL;


INSERT IGNORE INTO `consumers` (`users_id`, `consumers_levels_id`, `consumer_key`, `consumer_secret`, `callback_url`, `timestamp`) VALUES
(1, 3 ,'766bec602a9fe2795b43501ea4f9a9c9', AES_ENCRYPT('sad234fdsf243f4ff3f343kj43hj43g4hgf423f','xE7CF9Jkl2eZiLE8f1VFOCEqkUDSeW0'), '', '2011-01-28 20:10:07'),
(1, 3 ,'cb5408427d90b90c23a69dab73db1c71', AES_ENCRYPT('d1a73501b2ca2bcf5514fcaeb74572fb28bc693','xE7CF9Jkl2eZiLE8f1VFOCEqkUDSeW0'), '', '2011-01-28 20:10:07');


CREATE TABLE IF NOT EXISTS `consumers_levels` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL DEFAULT '',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=7;


INSERT IGNORE INTO `consumers_levels` (`id`, `name`) VALUES
(1, 'core'),
(2, 'api'),
(3, 'client'),
(4, 'super user'),
(5, 'user'),
(6, 'guest');

CREATE TABLE IF NOT EXISTS `users` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `active` int(11) NOT NULL DEFAULT 0,
  `username` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,  
  `bits_id` int(11) NOT NULL DEFAULT 1, 
  PRIMARY KEY (`id`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=2 ;

ALTER TABLE users CHANGE id id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT;
ALTER IGNORE TABLE `users` ADD `phone` BIGINT DEFAULT  NULL AFTER `email`;
ALTER IGNORE TABLE  `users` CHANGE  `email`  `email` VARCHAR( 255 ) DEFAULT NULL;
ALTER IGNORE TABLE `users` ADD `verified` INT( 11 ) NOT NULL DEFAULT 0 AFTER `active`;

INSERT IGNORE INTO `users` (`id`, `username`, `password`, `email`) VALUES
(1, 'platybox', AES_ENCRYPT('6cbe67de87317e1d888e8237a0313d8e','xE7CF9Jkl2eZiLE8f1VFOCEqkUDSeW0'), 'platybox@platybox.com');


CREATE TABLE IF NOT EXISTS `users_tokens_recover` (
  `users_id` int(11) NOT NULL DEFAULT 0,
  `token` varchar(255) NOT NULL,
  `timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP, 
  PRIMARY KEY (`users_id`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 ;

ALTER TABLE users_tokens_recover CHANGE users_id users_id BIGINT UNSIGNED NOT NULL DEFAULT 0;


CREATE TABLE IF NOT EXISTS `users_tokens_activate` (
  `users_id` int(11) NOT NULL DEFAULT 0,
  `token` varchar(255) NOT NULL,
  `timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP, 
  PRIMARY KEY (`users_id`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 ;

ALTER TABLE users_tokens_activate CHANGE users_id users_id BIGINT UNSIGNED NOT NULL DEFAULT 0;


CREATE TABLE IF NOT EXISTS `users_tokens_signup` (
  `users_id` int(11) NOT NULL DEFAULT 0,
  `token` varchar(255) NOT NULL,
  `timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP, 
  PRIMARY KEY (`users_id`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 ;


ALTER TABLE users_tokens_signup CHANGE users_id users_id BIGINT UNSIGNED NOT NULL DEFAULT 0;

CREATE TABLE IF NOT EXISTS `users_places` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `users_id` int(11) NOT NULL,
  `places_id` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1;


ALTER TABLE users_places CHANGE id id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT;
ALTER TABLE users_places CHANGE users_id users_id BIGINT UNSIGNED NOT NULL;
ALTER TABLE users_places CHANGE places_id places_id BIGINT UNSIGNED NOT NULL;


--
-- QUESTS OWNED (FINISHED) BY USERS
--

CREATE TABLE IF NOT EXISTS `users_quests` (
  `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  `users_id`BIGINT UNSIGNED NOT NULL,
  `quests_id` BIGINT UNSIGNED NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1;


ALTER IGNORE TABLE `users_quests` ADD `timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP AFTER `quests_id`;

--
-- USERS PROFILES
--

CREATE TABLE IF NOT EXISTS `users_profiles` (
  `users_id` int(11) NOT NULL,
  `name` varchar(255) NOT NULL DEFAULT 'A platypus.',
  `description` varchar(255) NOT NULL DEFAULT 'With our powers combined, we are platypus.',
  `photo` varchar(255) NOT NULL DEFAULT 'http://img.platybox.com/pr/platypus.jpg',
  PRIMARY KEY (`users_id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

ALTER TABLE users_profiles CHANGE users_id users_id BIGINT UNSIGNED NOT NULL;

INSERT IGNORE INTO `users_profiles` (`users_id`, `name`, `description`) VALUES
(1, 'Platybox Client', 'With our powers combined, we are platypus.');



CREATE TABLE IF NOT EXISTS `users_types` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `type` varchar(255) NOT NULL DEFAULT '',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=7;



INSERT IGNORE INTO `users_types` (`id`, `type`) VALUES
(1, 'user'),
(2, 'merchant');



CREATE TABLE IF NOT EXISTS `users_scores` (
  `users_id` int(11) NOT NULL,
  `points` int(11) NOT NULL DEFAULT '0',
  `promos` int(11) NOT NULL DEFAULT '0',
  `quests` int(11) NOT NULL DEFAULT '0',
  `coins` int(11) NOT NULL DEFAULT '0',
  `timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`users_id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

ALTER TABLE users_scores CHANGE users_id users_id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT;
ALTER TABLE users_scores CHANGE points points BIGINT UNSIGNED NOT NULL DEFAULT '0';
ALTER TABLE users_scores CHANGE promos promos BIGINT UNSIGNED NOT NULL DEFAULT '0';
ALTER TABLE users_scores CHANGE quests quests BIGINT UNSIGNED NOT NULL DEFAULT '0';
ALTER TABLE users_scores CHANGE coins coins BIGINT UNSIGNED NOT NULL DEFAULT '0';



CREATE TABLE IF NOT EXISTS `users_scores_places` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `users_id` int(11) NOT NULL,
  `places_id` int(11) NOT NULL,
  `points` int(11) NOT NULL DEFAULT 0,
  `promos` int(11) NOT NULL DEFAULT 0,
  `coins` int(11) NOT NULL DEFAULT 0,
  `timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  
  PRIMARY KEY (`id`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1;

ALTER TABLE users_scores_places CHANGE id id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT;
ALTER TABLE users_scores_places CHANGE users_id users_id BIGINT UNSIGNED NOT NULL;
ALTER TABLE users_scores_places CHANGE places_id places_id BIGINT UNSIGNED NOT NULL;
ALTER TABLE users_scores_places CHANGE points points BIGINT UNSIGNED NOT NULL DEFAULT '0';
ALTER TABLE users_scores_places CHANGE promos promos BIGINT UNSIGNED NOT NULL DEFAULT '0';
ALTER TABLE users_scores CHANGE coins coins BIGINT UNSIGNED NOT NULL DEFAULT '0';



CREATE TABLE IF NOT EXISTS `users_scores_quests` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `users_id` int(11) NOT NULL,
  `quests_id` int(11) NOT NULL,
  `points` int(11) NOT NULL DEFAULT 0,
  `experience` int(11) NOT NULL DEFAULT 0,
  `coins` int(11) NOT NULL DEFAULT 0,
  `timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  
  PRIMARY KEY (`id`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1;

ALTER TABLE users_scores_quests CHANGE id id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT;
ALTER TABLE users_scores_quests CHANGE users_id users_id BIGINT UNSIGNED NOT NULL;
ALTER TABLE users_scores_quests CHANGE quests_id quests_id BIGINT UNSIGNED NOT NULL;
ALTER TABLE users_scores_quests CHANGE points points BIGINT UNSIGNED NOT NULL DEFAULT 0;
ALTER TABLE users_scores_quests CHANGE experience experience BIGINT UNSIGNED NOT NULL DEFAULT 0;
ALTER TABLE users_scores_quests CHANGE coins coins BIGINT UNSIGNED NOT NULL DEFAULT 0;
ALTER IGNORE TABLE  `users_scores_quests` CHANGE  `experience` `quests` BIGINT UNSIGNED NOT NULL DEFAULT 0;


CREATE TABLE IF NOT EXISTS `friends` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `users_id` int(11) NOT NULL,
  `friend_users_id` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1;

ALTER TABLE friends CHANGE id id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT;
ALTER TABLE friends CHANGE users_id users_id BIGINT UNSIGNED NOT NULL;
ALTER TABLE friends CHANGE friend_users_id friend_users_id BIGINT UNSIGNED NOT NULL;



CREATE TABLE IF NOT EXISTS `links` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `users_id` int(11) NOT NULL,
  `link_bits_id` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1;

ALTER TABLE links CHANGE id id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT;
ALTER TABLE links CHANGE users_id users_id BIGINT UNSIGNED NOT NULL;
ALTER TABLE links CHANGE link_bits_id link_bits_id BIGINT UNSIGNED NOT NULL;


CREATE TABLE IF NOT EXISTS `checkins_bits` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `users_id` int(11) NOT NULL DEFAULT 0,
  `bits_id` int(11) NOT NULL DEFAULT 0,
  `timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1;

ALTER TABLE checkins_bits CHANGE id id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT;
ALTER TABLE checkins_bits CHANGE users_id users_id BIGINT UNSIGNED NOT NULL DEFAULT 0;
ALTER TABLE checkins_bits CHANGE bits_id bits_id BIGINT UNSIGNED NOT NULL DEFAULT 0;

CREATE TABLE IF NOT EXISTS `checkins_quests_bits` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `users_id` int(11) NOT NULL DEFAULT 0,
  `quests_bits_id` int(11) NOT NULL DEFAULT 0,
  `timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1;

ALTER TABLE checkins_quests_bits CHANGE id id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT;
ALTER TABLE checkins_quests_bits CHANGE users_id users_id BIGINT UNSIGNED NOT NULL DEFAULT 0;
ALTER TABLE checkins_quests_bits CHANGE quests_bits_id quests_bits_id BIGINT UNSIGNED NOT NULL DEFAULT 0;

CREATE TABLE IF NOT EXISTS `promos` (
  `id` int(11) NOT NULL AUTO_INCREMENT,  
  `bits_id` int(11) NOT NULL DEFAULT 0,
  `places_id` int(11) NOT NULL DEFAULT 1,
  `price` int(11) NOT NULL DEFAULT 0,
  `name` varchar(255) NOT NULL DEFAULT 'Promo!',
  `description` varchar(255) NOT NULL DEFAULT 'Awesome, you get something. Ask your server for details.',  
  `timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1;

ALTER TABLE promos CHANGE id id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT;
ALTER TABLE promos CHANGE bits_id bits_id BIGINT UNSIGNED NOT NULL DEFAULT 0;
ALTER TABLE promos CHANGE places_id places_id BIGINT UNSIGNED NOT NULL DEFAULT 1;
ALTER TABLE promos CHANGE price price BIGINT UNSIGNED NOT NULL DEFAULT 0;
ALTER TABLE promos CHANGE expires expires datetime DEFAULT '0000-00-00';

ALTER IGNORE TABLE  `promos` ADD `expires` datetime NOT NULL DEFAULT '30000101' AFTER `description`;

CREATE TABLE IF NOT EXISTS `promos_statuses` (
	`promos_id` int(11)NOT NULL,
	`sponsored` boolean NOT NULL DEFAULT false,
	`valid` boolean NOT NULL DEFAULT true,
	`available` boolean NOT NULL DEFAULT true,
  	`processing` boolean NOT NULL DEFAULT false,
	`users_id` int(11) NOT NULL DEFAULT 1,
  PRIMARY KEY (`promos_id`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1;

ALTER TABLE promos_statuses CHANGE promos_id promos_id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT;
ALTER TABLE promos_statuses CHANGE users_id users_id BIGINT UNSIGNED NOT NULL DEFAULT 1;



CREATE TABLE IF NOT EXISTS `promos_schedules` (
	`promos_id` int(11) NOT NULL,
	`dtstart` varchar(510) NULL DEFAULT NULL,
	`dtend` varchar(510) NULL DEFAULT NULL,
	`rrule` varchar(510) NULL DEFAULT NULL,
	`rdatelist` varchar(510) NULL DEFAULT NULL,
  PRIMARY KEY (`promos_id`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1;

ALTER TABLE promos_schedules CHANGE promos_id promos_id BIGINT UNSIGNED NOT NULL;


CREATE TABLE IF NOT EXISTS `promos_pos_squirrel` (
	`promos_id` int(11)NOT NULL,
	`pos_id` int(11)NOT NULL,
	`positem` varchar(255) NULL DEFAULT NULL,
	`pospromo` varchar(255) NULL DEFAULT NULL,
	`posbadge` varchar(255) NULL DEFAULT NULL,
	`posdepartment` varchar(255) NULL DEFAULT NULL,
	`postable` varchar(255) NULL DEFAULT NULL,
	`posseat` varchar(255) NULL DEFAULT NULL,	
  PRIMARY KEY (`promos_id`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1;

INSERT IGNORE INTO `promos_pos_squirrel` (`promos_id`, `pos_id`, `positem`,`pospromo` ,`posbadge`, `posdepartment`, `postable`, `posseat`) VALUES
(18, 5, 502, 2, 22, 5, 5, 1);

ALTER TABLE promos_pos_squirrel CHANGE promos_id promos_id BIGINT UNSIGNED NOT NULL;
ALTER TABLE promos_pos_squirrel CHANGE pos_id pos_id BIGINT UNSIGNED NOT NULL;

--
-- TABLE CONTAINING POS USERS PER PLACE AND THEIR CONFIGURATIONSS 
--

CREATE TABLE IF NOT EXISTS `pos` (
	`id` int(11) NOT NULL AUTO_INCREMENT,
	`pos_types_id` int(11) NOT NULL,
	`places_id` int(11) NOT NULL DEFAULT '1',
	`posaddress` varchar(255) NULL DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=6;

ALTER TABLE pos CHANGE id id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT;
ALTER TABLE pos CHANGE places_id places_id BIGINT UNSIGNED NOT NULL DEFAULT '1';
ALTER IGNORE TABLE `pos` ADD `pos_receipt_messages_id` INT( 11 ) NOT NULL DEFAULT  '1' AFTER `posaddress`;

INSERT IGNORE INTO `pos` (`id`, `pos_types_id`, `places_id`,`posaddress`) VALUES
(1, 2, 1, '+17789967359'),
(2, 2, 2, '+6043151819'),
(3, 1, 3, NULL),
(4, 1, 4, NULL),
(5, 3, 5, '128.189.226.149:4998');

--
-- TABLE CONTAINING POS TYPES
--

CREATE TABLE IF NOT EXISTS `pos_types` (
  `id` int(11) NOT NULL AUTO_INCREMENT,  
  `type` varchar(255) NOT NULL DEFAULT '',  
  PRIMARY KEY (`id`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=4;


INSERT IGNORE INTO `pos_types` (`id`, `type`) VALUES
(1, 'none'),
(2, 'sms'),
(3, 'squirrel');


-- 
-- TABLE CONTAINING DEFAULT MESSAGES
-- 

CREATE TABLE IF NOT EXISTS `pos_receipt_messages` (
  `id` int(11) NOT NULL AUTO_INCREMENT,  
  `header` varchar(255) NOT NULL DEFAULT '',
  `welcome` varchar(255) NOT NULL DEFAULT '',
  `checkin` varchar(255) NOT NULL DEFAULT '',
  `phone` varchar(255) NOT NULL DEFAULT '',
  `url` varchar(255) NOT NULL DEFAULT '',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=2;


INSERT IGNORE INTO `pos_receipt_messages` (`id`, `header`, `welcome`,`checkin`,`phone`,`url`) VALUES
(1,
 "      .--..-'''-''''-._   THIS PLACE IS A#&  ___/%   ) )      \\  ;-;;,_   LOCAL GEM#&((:___/--/ /--------\\ ) `'-'#&         ''           ''#&",
 "    WIN $20-$100 gift cards!",
 "Text:#&",
 " to 604 800-9199#&",
 "    Details at www.chowvancouver.com"
 );

--
-- QUESTS AVAILABLE, VALUE_NEEDED MARKS HOW MANY POINTS COLLECTED THROUGH QUESTS_BITS ARE NEEDED TO COMPLETE THIS QUEST
--

CREATE TABLE IF NOT EXISTS `quests` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `quests_types_id` int(11) NOT NULL DEFAULT 1,
  `quests_badges_id` int(11) NOT NULL DEFAULT 1,
  `name` varchar(255) NOT NULL DEFAULT 'The quest for awesomeness.',
  `description` varchar(255) NOT NULL DEFAULT 'Begin your quest with collecting Box 60 Inc.',
  `available` boolean NOT NULL DEFAULT false,    
  PRIMARY KEY (`id`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=2;

ALTER TABLE quests CHANGE id id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT;
ALTER TABLE  `quests` ADD  `quests_bits_value_needed` INT NOT NULL DEFAULT  '1' AFTER  `description`;

INSERT IGNORE INTO `quests` (`id`, `quests_types_id`, `quests_badges_id`, `name`, `description`, `available`) VALUES
(1, 1, 1 , 'Awesomeness.', 'Explore the lands of Box 60 Inc.', false);

--
-- THE TYPE OF QUEST
--

CREATE TABLE IF NOT EXISTS `quests_types` (
  `id` int(11) NOT NULL AUTO_INCREMENT,  
  `type` varchar(255) NOT NULL DEFAULT '',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=4;


INSERT IGNORE INTO `quests_types` (`id`, `type`) VALUES
(1, 'discover');

--
-- BADGES THAT CAN BE OBTAINED THROUGH QUESTS
--

CREATE TABLE IF NOT EXISTS `quests_badges` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL DEFAULT 'noob',
  `image_url` varchar(255) NOT NULL DEFAULT 'http://img.platybox.com/ba/noob.jpg',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=2;

INSERT IGNORE INTO `quests_badges` (`id`, `name`, `image_url`) VALUES
(1, 'noob','http://img.platybox.com/ba/noob.jpg');


--
-- BITS THAT BELONG TO A QUEST. VALUE IS USED TO COMPUTE WHEN A QUEST IS FINISHED, NOT ALL BITS HAVE TO BE COLLECTED TO
-- FINISH A QUEST, THIS IS, BECAUSE SOME PLACES HAVE MULTIPLE BITS.
--

CREATE TABLE IF NOT EXISTS `quests_bits` (
  `id` int(11) NOT NULL AUTO_INCREMENT,  
  `quests_id` int(11) NOT NULL DEFAULT 1,
  `bits_id` int(11) NOT NULL DEFAULT 2,
  `value` int(11) NOT NULL DEFAULT 1,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=2;


ALTER TABLE quests_bits CHANGE id id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT;
ALTER TABLE quests_bits CHANGE quests_id quests_id BIGINT UNSIGNED NOT NULL DEFAULT 1;
ALTER TABLE quests_bits CHANGE bits_id bits_id BIGINT UNSIGNED NOT NULL DEFAULT 2;

--
-- QUEUE FOR EMAILS THAT NEED TO BE SENT
--

CREATE TABLE IF NOT EXISTS `emails_queue` (
  `id` int(11) NOT NULL AUTO_INCREMENT,  
  `users_id` int(11),
  `emails_queue_types_id`  int(11),
  `processing`  BOOLEAN DEFAULT false,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=1;

ALTER TABLE emails_queue CHANGE id id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT;
ALTER TABLE emails_queue CHANGE users_id users_id BIGINT UNSIGNED NOT NULL;
ALTER TABLE emails_queue ADD `promos_id` BIGINT UNSIGNED DEFAULT NULL AFTER `emails_queue_types_id`;

UPDATE emails_queue SET processing=false WHERE processing=true;


--
-- TYPE OF EMAIL THAT NEED TO E SENT
--

CREATE TABLE IF NOT EXISTS `emails_queue_types` (
  `id` int(11) NOT NULL AUTO_INCREMENT,  
  `type` varchar(255) NOT NULL DEFAULT '',  
  PRIMARY KEY (`id`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=4;


INSERT IGNORE INTO `emails_queue_types` (`id`, `type`) VALUES
(1, 'register'),
(2, 'signup'),
(3, 'recover'),
(4, 'coupon');

--
-- Coupons that can be bought our given away
--

CREATE TABLE IF NOT EXISTS `coupons` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `coupons_types_id` varchar(255) NOT NULL DEFAULT '2',
  `places_id` int(11),
  `price` int(11) NOT NULL DEFAULT 10,
  `name` varchar(255) NOT NULL DEFAULT 'Register awesomeness!',
  `description` varchar(255) NOT NULL DEFAULT 'Awesome, you get something. Ask your server for details.',
  `expires` date DEFAULT '30000101',
  `left` int(11) NOT NULL DEFAULT 0,
  `available` boolean NOT NULL DEFAULT false,
  
  PRIMARY KEY (`id`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=1;

ALTER TABLE coupons CHANGE id id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT;
ALTER TABLE coupons CHANGE places_id places_id BIGINT UNSIGNED NOT NULL;
ALTER TABLE coupons CHANGE price price BIGINT UNSIGNED NOT NULL DEFAULT 10;
ALTER TABLE coupons ADD `quests_id` BIGINT UNSIGNED NOT NULL DEFAULT 0 AFTER `places_id`;
ALTER TABLE coupons ADD `bits_id` BIGINT UNSIGNED NOT NULL DEFAULT 0 AFTER `quests_id`;

CREATE TABLE IF NOT EXISTS `coupons_types` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `type` varchar(255) NOT NULL DEFAULT '',  
  PRIMARY KEY (`id`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=4;


INSERT IGNORE INTO `coupons_types` (`id`, `type`) VALUES
(1, 'register'),
(2, 'checkin'),
(3, 'quest');


--
-- COUPONS PURCHASED OR GIVEN AWAY, FOR OUR RECORDS
--

CREATE TABLE IF NOT EXISTS `coupons_purchased` (
  `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  `users_id` BIGINT UNSIGNED NOT NULL DEFAULT 0,
  `coupons_id` BIGINT UNSIGNED NOT NULL DEFAULT 0,
  `timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1;

--
-- ANALYTICS FOR BITS BEING SCANNED
--

CREATE TABLE IF NOT EXISTS `bits_scanned` (
  `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  `bits_id` BIGINT UNSIGNED NOT NULL DEFAULT 0,
  `timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1;


--
-- ANALYTICS FOR REGISTRATIONS THROUGH A BIT
--

CREATE TABLE IF NOT EXISTS `bits_registrations` (
  `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  `bits_id` BIGINT UNSIGNED NOT NULL DEFAULT 0,
  `users_id` BIGINT UNSIGNED NOT NULL DEFAULT 0,
  `timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1;

--
-- STEPS WHERE A PARTICULAR PHONE IS AT IN THE REGISTRATION PROCESS, and the messages that will be sent back.
-- Note: users_id is a temporary column in the registration process, it DOES NOT link to a real user id. 
--

CREATE TABLE IF NOT EXISTS `phones_statuses` (
  `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  `phone` BIGINT UNSIGNED DEFAULT NULL,
  `bits_id` BIGINT UNSIGNED DEFAULT NULL,
  `users_id` BIGINT UNSIGNED DEFAULT NULL,
  `phones_statuses_types_id` BIGINT UNSIGNED NOT NULL DEFAULT 0,
  `timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1;

CREATE TABLE IF NOT EXISTS `phones_statuses_types` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `type` varchar(255) NOT NULL DEFAULT '',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=11;

INSERT IGNORE INTO `phones_statuses_types` (`id`, `type`) VALUES
-- Stopped
(0, 'stop'),
-- Wrong Code
(1, 'wrongcode'),
(2, 'welcome'),
-- Signup
(3, 'signupemail'),
(4, 'signupemailerror'),
(5, 'signupemailsuccess'),
-- Register phone
(6, 'registerusernameerror'),
(7, 'registerpassword'),
(8, 'registerpassworderror'),
(9, 'registersuccess'),
-- Ended
(10, 'registered');

CREATE TABLE IF NOT EXISTS `phones_statuses_messages` (
  `phones_statuses_types_id` int(11) NOT NULL AUTO_INCREMENT,
  `message` varchar(255) NOT NULL DEFAULT '',  
  PRIMARY KEY (`phones_statuses_types_id`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=11;

INSERT IGNORE INTO `phones_statuses_messages` (`phones_statuses_types_id`, `message`) VALUES
(0, 'The pink platypus says bye.'),
(1, 'Welcome to Platybox! OOps, you miszpelled that code, or this code is no longer valid. Details at chowvancouver.com'),
(2, 'Thanks for participating!\n\nWant a Platybox? Reply w/SIGNUP. Already on Platybox? Reply with your USERNAME (std fees apply). platybox.com'),
(3, 'Great! Reply w/your EMAIL so we can send you an invite. Details at chowvancouver.com\n\nReply with STOP to cancel.'),
(4, 'You mizspelled your email or email already registered. Reply with your EMAIL.\n\nReply with STOP to cancel.'),
(5, 'We have sent you an invite. Check your email to activate your account.'),
(6, 'You mizspelled your username. reply with your USERNAME.\n\nReply with STOP to cancel.'),
(7, 'Reply w/ your PASSWORD to register this phone to your Platybox account. (Password is case sensitive! Phone may capitalize the 1st letter).'),
(8, 'You mizspelled your password. reply with your PASSWORD.'),
(9, 'Password is correct. You have registered this phone to your Platybox account.'),
(10, 'Got you!');

