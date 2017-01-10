INSERT INTO `STJ_SEASON` (`CRTN_DT`, `CRTN_USER_ID`, `LAST_UPTD_DT`, `LAST_UPTD_USER_ID`, `YEAR`) VALUES
(now(), 'SYSTEM', now(), 'SYSTEM', 2017);

INSERT INTO `STJ_ROUND` (`CRTN_DT`, `CRTN_USER_ID`, `LAST_UPTD_DT`, `LAST_UPTD_USER_ID`, `NAME`, `SEASON_ID`) VALUES
(now(), 'SYSTEM', now(), 'SYSTEM', 'ROUND 1', (select SEASON_ID from STJ_SEASON where YEAR = 2017)),
(now(), 'SYSTEM', now(), 'SYSTEM', 'ROUND 2', (select SEASON_ID from STJ_SEASON where YEAR = 2017));

INSERT INTO `STJ_SEASON_TEAM` (`SEASON_ID`, `TEAM_ID`) VALUES
((select SEASON_ID from STJ_SEASON where YEAR = 2017), 1),
((select SEASON_ID from STJ_SEASON where YEAR = 2017), 2),
((select SEASON_ID from STJ_SEASON where YEAR = 2017), 3),
((select SEASON_ID from STJ_SEASON where YEAR = 2017), 4),
((select SEASON_ID from STJ_SEASON where YEAR = 2017), 5),
((select SEASON_ID from STJ_SEASON where YEAR = 2017), 6),
((select SEASON_ID from STJ_SEASON where YEAR = 2017), 7),
((select SEASON_ID from STJ_SEASON where YEAR = 2017), 8),
((select SEASON_ID from STJ_SEASON where YEAR = 2017), 9),
((select SEASON_ID from STJ_SEASON where YEAR = 2017), 10),
((select SEASON_ID from STJ_SEASON where YEAR = 2017), 11),
((select SEASON_ID from STJ_SEASON where YEAR = 2017), 12);


INSERT INTO `STJ_WEEK` (`CRTN_DT`, `CRTN_USER_ID`, `LAST_UPTD_DT`, `LAST_UPTD_USER_ID`, `WK_DATE`, `TEAM_MATCH_1_ID`, `TEAM_MATCH_2_ID`, `TEAM_MATCH_3_ID`, `TEAM_MATCH_4_ID`, `TEAM_MATCH_5_ID`, `TEAM_MATCH_6_ID`, `ROUND_ID`) VALUES
(now(), 'SYSTEM', now(), 'SYSTEM', '2017-04-07', null, null, null, null, null, null, (select ROUND_ID from STJ_ROUND where NAME = 'ROUND 1' and SEASON_ID = (select SEASON_ID from STJ_SEASON where YEAR = 2017))),
(now(), 'SYSTEM', now(), 'SYSTEM', '2017-04-14', null, null, null, null, null, null, (select ROUND_ID from STJ_ROUND where NAME = 'ROUND 1' and SEASON_ID = (select SEASON_ID from STJ_SEASON where YEAR = 2017))),
(now(), 'SYSTEM', now(), 'SYSTEM', '2017-04-21', null, null, null, null, null, null, (select ROUND_ID from STJ_ROUND where NAME = 'ROUND 1' and SEASON_ID = (select SEASON_ID from STJ_SEASON where YEAR = 2017))),
(now(), 'SYSTEM', now(), 'SYSTEM', '2017-04-28', null, null, null, null, null, null, (select ROUND_ID from STJ_ROUND where NAME = 'ROUND 1' and SEASON_ID = (select SEASON_ID from STJ_SEASON where YEAR = 2017))),
(now(), 'SYSTEM', now(), 'SYSTEM', '2017-05-05', null, null, null, null, null, null, (select ROUND_ID from STJ_ROUND where NAME = 'ROUND 1' and SEASON_ID = (select SEASON_ID from STJ_SEASON where YEAR = 2017))),
(now(), 'SYSTEM', now(), 'SYSTEM', '2017-05-12', null, null, null, null, null, null, (select ROUND_ID from STJ_ROUND where NAME = 'ROUND 1' and SEASON_ID = (select SEASON_ID from STJ_SEASON where YEAR = 2017))),
(now(), 'SYSTEM', now(), 'SYSTEM', '2017-05-19', null, null, null, null, null, null, (select ROUND_ID from STJ_ROUND where NAME = 'ROUND 1' and SEASON_ID = (select SEASON_ID from STJ_SEASON where YEAR = 2017))),
(now(), 'SYSTEM', now(), 'SYSTEM', '2017-05-26', null, null, null, null, null, null, (select ROUND_ID from STJ_ROUND where NAME = 'ROUND 1' and SEASON_ID = (select SEASON_ID from STJ_SEASON where YEAR = 2017))),
(now(), 'SYSTEM', now(), 'SYSTEM', '2017-06-02', null, null, null, null, null, null, (select ROUND_ID from STJ_ROUND where NAME = 'ROUND 1' and SEASON_ID = (select SEASON_ID from STJ_SEASON where YEAR = 2017))),
(now(), 'SYSTEM', now(), 'SYSTEM', '2017-06-09', null, null, null, null, null, null, (select ROUND_ID from STJ_ROUND where NAME = 'ROUND 1' and SEASON_ID = (select SEASON_ID from STJ_SEASON where YEAR = 2017))),
(now(), 'SYSTEM', now(), 'SYSTEM', '2017-06-16', null, null, null, null, null, null, (select ROUND_ID from STJ_ROUND where NAME = 'ROUND 1' and SEASON_ID = (select SEASON_ID from STJ_SEASON where YEAR = 2017))),
(now(), 'SYSTEM', now(), 'SYSTEM', '2017-06-23', null, null, null, null, null, null, (select ROUND_ID from STJ_ROUND where NAME = 'ROUND 2' and SEASON_ID = (select SEASON_ID from STJ_SEASON where YEAR = 2017))),
(now(), 'SYSTEM', now(), 'SYSTEM', '2017-06-30', null, null, null, null, null, null, (select ROUND_ID from STJ_ROUND where NAME = 'ROUND 2' and SEASON_ID = (select SEASON_ID from STJ_SEASON where YEAR = 2017))),
(now(), 'SYSTEM', now(), 'SYSTEM', '2017-07-07', null, null, null, null, null, null, (select ROUND_ID from STJ_ROUND where NAME = 'ROUND 2' and SEASON_ID = (select SEASON_ID from STJ_SEASON where YEAR = 2017))),
(now(), 'SYSTEM', now(), 'SYSTEM', '2017-07-14', null, null, null, null, null, null, (select ROUND_ID from STJ_ROUND where NAME = 'ROUND 2' and SEASON_ID = (select SEASON_ID from STJ_SEASON where YEAR = 2017))),
(now(), 'SYSTEM', now(), 'SYSTEM', '2017-07-21', null, null, null, null, null, null, (select ROUND_ID from STJ_ROUND where NAME = 'ROUND 2' and SEASON_ID = (select SEASON_ID from STJ_SEASON where YEAR = 2017))),
(now(), 'SYSTEM', now(), 'SYSTEM', '2017-07-28', null, null, null, null, null, null, (select ROUND_ID from STJ_ROUND where NAME = 'ROUND 2' and SEASON_ID = (select SEASON_ID from STJ_SEASON where YEAR = 2017))),
(now(), 'SYSTEM', now(), 'SYSTEM', '2017-08-04', null, null, null, null, null, null, (select ROUND_ID from STJ_ROUND where NAME = 'ROUND 2' and SEASON_ID = (select SEASON_ID from STJ_SEASON where YEAR = 2017))),
(now(), 'SYSTEM', now(), 'SYSTEM', '2017-08-11', null, null, null, null, null, null, (select ROUND_ID from STJ_ROUND where NAME = 'ROUND 2' and SEASON_ID = (select SEASON_ID from STJ_SEASON where YEAR = 2017))),
(now(), 'SYSTEM', now(), 'SYSTEM', '2017-08-18', null, null, null, null, null, null, (select ROUND_ID from STJ_ROUND where NAME = 'ROUND 2' and SEASON_ID = (select SEASON_ID from STJ_SEASON where YEAR = 2017))),
(now(), 'SYSTEM', now(), 'SYSTEM', '2017-08-25', null, null, null, null, null, null, (select ROUND_ID from STJ_ROUND where NAME = 'ROUND 2' and SEASON_ID = (select SEASON_ID from STJ_SEASON where YEAR = 2017))),
(now(), 'SYSTEM', now(), 'SYSTEM', '2017-09-01', null, null, null, null, null, null, (select ROUND_ID from STJ_ROUND where NAME = 'ROUND 2' and SEASON_ID = (select SEASON_ID from STJ_SEASON where YEAR = 2017)));

UPDATE STJ_PLAYER SET skins_start_date = null;