INSERT INTO `STJ_SEASON` (`CRTN_DT`, `CRTN_USER_ID`, `LAST_UPTD_DT`, `LAST_UPTD_USER_ID`, `YEAR`) VALUES
(now(), 'SYSTEM', now(), 'SYSTEM', 2016);

INSERT INTO `STJ_ROUND` (`CRTN_DT`, `CRTN_USER_ID`, `LAST_UPTD_DT`, `LAST_UPTD_USER_ID`, `NAME`, `SEASON_ID`) VALUES
(now(), 'SYSTEM', now(), 'SYSTEM', 'ROUND 1', (select SEASON_ID from STJ_SEASON where YEAR = 2016)),
(now(), 'SYSTEM', now(), 'SYSTEM', 'ROUND 2', (select SEASON_ID from STJ_SEASON where YEAR = 2016));

INSERT INTO `STJ_SEASON_TEAM` (`SEASON_ID`, `TEAM_ID`) VALUES
((select SEASON_ID from STJ_SEASON where YEAR = 2016), 1),
((select SEASON_ID from STJ_SEASON where YEAR = 2016), 2),
((select SEASON_ID from STJ_SEASON where YEAR = 2016), 3),
((select SEASON_ID from STJ_SEASON where YEAR = 2016), 4),
((select SEASON_ID from STJ_SEASON where YEAR = 2016), 5),
((select SEASON_ID from STJ_SEASON where YEAR = 2016), 6),
((select SEASON_ID from STJ_SEASON where YEAR = 2016), 7),
((select SEASON_ID from STJ_SEASON where YEAR = 2016), 8),
((select SEASON_ID from STJ_SEASON where YEAR = 2016), 9),
((select SEASON_ID from STJ_SEASON where YEAR = 2016), 10),
((select SEASON_ID from STJ_SEASON where YEAR = 2016), 11),
((select SEASON_ID from STJ_SEASON where YEAR = 2016), 12);


INSERT INTO `STJ_WEEK` (`CRTN_DT`, `CRTN_USER_ID`, `LAST_UPTD_DT`, `LAST_UPTD_USER_ID`, `WK_DATE`, `TEAM_MATCH_1_ID`, `TEAM_MATCH_2_ID`, `TEAM_MATCH_3_ID`, `TEAM_MATCH_4_ID`, `TEAM_MATCH_5_ID`, `TEAM_MATCH_6_ID`, `ROUND_ID`) VALUES
(now(), 'SYSTEM', now(), 'SYSTEM', '2016-04-08', null, null, null, null, null, null, (select ROUND_ID from STJ_ROUND where NAME = 'ROUND 1' and SEASON_ID = (select SEASON_ID from STJ_SEASON where YEAR = 2016))),
(now(), 'SYSTEM', now(), 'SYSTEM', '2016-04-15', null, null, null, null, null, null, (select ROUND_ID from STJ_ROUND where NAME = 'ROUND 1' and SEASON_ID = (select SEASON_ID from STJ_SEASON where YEAR = 2016))),
(now(), 'SYSTEM', now(), 'SYSTEM', '2016-04-22', null, null, null, null, null, null, (select ROUND_ID from STJ_ROUND where NAME = 'ROUND 1' and SEASON_ID = (select SEASON_ID from STJ_SEASON where YEAR = 2016))),
(now(), 'SYSTEM', now(), 'SYSTEM', '2016-04-29', null, null, null, null, null, null, (select ROUND_ID from STJ_ROUND where NAME = 'ROUND 1' and SEASON_ID = (select SEASON_ID from STJ_SEASON where YEAR = 2016))),
(now(), 'SYSTEM', now(), 'SYSTEM', '2016-05-06', null, null, null, null, null, null, (select ROUND_ID from STJ_ROUND where NAME = 'ROUND 1' and SEASON_ID = (select SEASON_ID from STJ_SEASON where YEAR = 2016))),
(now(), 'SYSTEM', now(), 'SYSTEM', '2016-05-13', null, null, null, null, null, null, (select ROUND_ID from STJ_ROUND where NAME = 'ROUND 1' and SEASON_ID = (select SEASON_ID from STJ_SEASON where YEAR = 2016))),
(now(), 'SYSTEM', now(), 'SYSTEM', '2016-05-20', null, null, null, null, null, null, (select ROUND_ID from STJ_ROUND where NAME = 'ROUND 1' and SEASON_ID = (select SEASON_ID from STJ_SEASON where YEAR = 2016))),
(now(), 'SYSTEM', now(), 'SYSTEM', '2016-05-27', null, null, null, null, null, null, (select ROUND_ID from STJ_ROUND where NAME = 'ROUND 1' and SEASON_ID = (select SEASON_ID from STJ_SEASON where YEAR = 2016))),
(now(), 'SYSTEM', now(), 'SYSTEM', '2016-06-03', null, null, null, null, null, null, (select ROUND_ID from STJ_ROUND where NAME = 'ROUND 1' and SEASON_ID = (select SEASON_ID from STJ_SEASON where YEAR = 2016))),
(now(), 'SYSTEM', now(), 'SYSTEM', '2016-06-10', null, null, null, null, null, null, (select ROUND_ID from STJ_ROUND where NAME = 'ROUND 1' and SEASON_ID = (select SEASON_ID from STJ_SEASON where YEAR = 2016))),
(now(), 'SYSTEM', now(), 'SYSTEM', '2016-06-17', null, null, null, null, null, null, (select ROUND_ID from STJ_ROUND where NAME = 'ROUND 1' and SEASON_ID = (select SEASON_ID from STJ_SEASON where YEAR = 2016))),
(now(), 'SYSTEM', now(), 'SYSTEM', '2016-06-24', null, null, null, null, null, null, (select ROUND_ID from STJ_ROUND where NAME = 'ROUND 2' and SEASON_ID = (select SEASON_ID from STJ_SEASON where YEAR = 2016))),
(now(), 'SYSTEM', now(), 'SYSTEM', '2016-07-01', null, null, null, null, null, null, (select ROUND_ID from STJ_ROUND where NAME = 'ROUND 2' and SEASON_ID = (select SEASON_ID from STJ_SEASON where YEAR = 2016))),
(now(), 'SYSTEM', now(), 'SYSTEM', '2016-07-08', null, null, null, null, null, null, (select ROUND_ID from STJ_ROUND where NAME = 'ROUND 2' and SEASON_ID = (select SEASON_ID from STJ_SEASON where YEAR = 2016))),
(now(), 'SYSTEM', now(), 'SYSTEM', '2016-07-15', null, null, null, null, null, null, (select ROUND_ID from STJ_ROUND where NAME = 'ROUND 2' and SEASON_ID = (select SEASON_ID from STJ_SEASON where YEAR = 2016))),
(now(), 'SYSTEM', now(), 'SYSTEM', '2016-07-22', null, null, null, null, null, null, (select ROUND_ID from STJ_ROUND where NAME = 'ROUND 2' and SEASON_ID = (select SEASON_ID from STJ_SEASON where YEAR = 2016))),
(now(), 'SYSTEM', now(), 'SYSTEM', '2016-07-29', null, null, null, null, null, null, (select ROUND_ID from STJ_ROUND where NAME = 'ROUND 2' and SEASON_ID = (select SEASON_ID from STJ_SEASON where YEAR = 2016))),
(now(), 'SYSTEM', now(), 'SYSTEM', '2016-08-05', null, null, null, null, null, null, (select ROUND_ID from STJ_ROUND where NAME = 'ROUND 2' and SEASON_ID = (select SEASON_ID from STJ_SEASON where YEAR = 2016))),
(now(), 'SYSTEM', now(), 'SYSTEM', '2016-08-12', null, null, null, null, null, null, (select ROUND_ID from STJ_ROUND where NAME = 'ROUND 2' and SEASON_ID = (select SEASON_ID from STJ_SEASON where YEAR = 2016))),
(now(), 'SYSTEM', now(), 'SYSTEM', '2016-08-19', null, null, null, null, null, null, (select ROUND_ID from STJ_ROUND where NAME = 'ROUND 2' and SEASON_ID = (select SEASON_ID from STJ_SEASON where YEAR = 2016))),
(now(), 'SYSTEM', now(), 'SYSTEM', '2016-08-26', null, null, null, null, null, null, (select ROUND_ID from STJ_ROUND where NAME = 'ROUND 2' and SEASON_ID = (select SEASON_ID from STJ_SEASON where YEAR = 2016))),
(now(), 'SYSTEM', now(), 'SYSTEM', '2016-09-02', null, null, null, null, null, null, (select ROUND_ID from STJ_ROUND where NAME = 'ROUND 2' and SEASON_ID = (select SEASON_ID from STJ_SEASON where YEAR = 2016)));

UPDATE STJ_PLAYER SET skins_start_date = null;