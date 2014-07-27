INSERT INTO `STJ_SEASON` (`CRTN_DT`, `CRTN_USER_ID`, `LAST_UPTD_DT`, `LAST_UPTD_USER_ID`, `YEAR`) VALUES
(now(), 'SYSTEM', now(), 'SYSTEM', 2014);

INSERT INTO `STJ_ROUND` (`CRTN_DT`, `CRTN_USER_ID`, `LAST_UPTD_DT`, `LAST_UPTD_USER_ID`, `NAME`, `SEASON_ID`) VALUES
(now(), 'SYSTEM', now(), 'SYSTEM', 'ROUND 1', (select SEASON_ID from STJ_SEASON where YEAR = 2014)),
(now(), 'SYSTEM', now(), 'SYSTEM', 'ROUND 2', (select SEASON_ID from STJ_SEASON where YEAR = 2014));

INSERT INTO `STJ_SEASON_TEAM` (`SEASON_ID`, `TEAM_ID`) VALUES
((select SEASON_ID from STJ_SEASON where YEAR = 2014), 1),
((select SEASON_ID from STJ_SEASON where YEAR = 2014), 2),
((select SEASON_ID from STJ_SEASON where YEAR = 2014), 3),
((select SEASON_ID from STJ_SEASON where YEAR = 2014), 4),
((select SEASON_ID from STJ_SEASON where YEAR = 2014), 5),
((select SEASON_ID from STJ_SEASON where YEAR = 2014), 6),
((select SEASON_ID from STJ_SEASON where YEAR = 2014), 7),
((select SEASON_ID from STJ_SEASON where YEAR = 2014), 8),
((select SEASON_ID from STJ_SEASON where YEAR = 2014), 9),
((select SEASON_ID from STJ_SEASON where YEAR = 2014), 10),
((select SEASON_ID from STJ_SEASON where YEAR = 2014), 11),
((select SEASON_ID from STJ_SEASON where YEAR = 2014), 12);


INSERT INTO `STJ_WEEK` (`CRTN_DT`, `CRTN_USER_ID`, `LAST_UPTD_DT`, `LAST_UPTD_USER_ID`, `WK_DATE`, `TEAM_MATCH_1_ID`, `TEAM_MATCH_2_ID`, `TEAM_MATCH_3_ID`, `TEAM_MATCH_4_ID`, `TEAM_MATCH_5_ID`, `TEAM_MATCH_6_ID`, `ROUND_ID`) VALUES
(now(), 'SYSTEM', now(), 'SYSTEM', '2014-04-4', null, null, null, null, null, null, (select ROUND_ID from STJ_ROUND where NAME = 'ROUND 1' and SEASON_ID = (select SEASON_ID from STJ_SEASON where YEAR = 2014))),
(now(), 'SYSTEM', now(), 'SYSTEM', '2014-04-11', null, null, null, null, null, null, (select ROUND_ID from STJ_ROUND where NAME = 'ROUND 1' and SEASON_ID = (select SEASON_ID from STJ_SEASON where YEAR = 2014))),
(now(), 'SYSTEM', now(), 'SYSTEM', '2014-04-25', null, null, null, null, null, null, (select ROUND_ID from STJ_ROUND where NAME = 'ROUND 1' and SEASON_ID = (select SEASON_ID from STJ_SEASON where YEAR = 2014))),
(now(), 'SYSTEM', now(), 'SYSTEM', '2014-05-02', null, null, null, null, null, null, (select ROUND_ID from STJ_ROUND where NAME = 'ROUND 1' and SEASON_ID = (select SEASON_ID from STJ_SEASON where YEAR = 2014))),
(now(), 'SYSTEM', now(), 'SYSTEM', '2014-05-9', null, null, null, null, null, null, (select ROUND_ID from STJ_ROUND where NAME = 'ROUND 1' and SEASON_ID = (select SEASON_ID from STJ_SEASON where YEAR = 2014))),
(now(), 'SYSTEM', now(), 'SYSTEM', '2014-05-16', null, null, null, null, null, null, (select ROUND_ID from STJ_ROUND where NAME = 'ROUND 1' and SEASON_ID = (select SEASON_ID from STJ_SEASON where YEAR = 2014))),
(now(), 'SYSTEM', now(), 'SYSTEM', '2014-05-23', null, null, null, null, null, null, (select ROUND_ID from STJ_ROUND where NAME = 'ROUND 1' and SEASON_ID = (select SEASON_ID from STJ_SEASON where YEAR = 2014))),
(now(), 'SYSTEM', now(), 'SYSTEM', '2014-05-30', null, null, null, null, null, null, (select ROUND_ID from STJ_ROUND where NAME = 'ROUND 1' and SEASON_ID = (select SEASON_ID from STJ_SEASON where YEAR = 2014))),
(now(), 'SYSTEM', now(), 'SYSTEM', '2014-06-06', null, null, null, null, null, null, (select ROUND_ID from STJ_ROUND where NAME = 'ROUND 1' and SEASON_ID = (select SEASON_ID from STJ_SEASON where YEAR = 2014))),
(now(), 'SYSTEM', now(), 'SYSTEM', '2014-06-13', null, null, null, null, null, null, (select ROUND_ID from STJ_ROUND where NAME = 'ROUND 1' and SEASON_ID = (select SEASON_ID from STJ_SEASON where YEAR = 2014))),
(now(), 'SYSTEM', now(), 'SYSTEM', '2014-06-20', null, null, null, null, null, null, (select ROUND_ID from STJ_ROUND where NAME = 'ROUND 1' and SEASON_ID = (select SEASON_ID from STJ_SEASON where YEAR = 2014))),
(now(), 'SYSTEM', now(), 'SYSTEM', '2014-06-27', null, null, null, null, null, null, (select ROUND_ID from STJ_ROUND where NAME = 'ROUND 2' and SEASON_ID = (select SEASON_ID from STJ_SEASON where YEAR = 2014))),
(now(), 'SYSTEM', now(), 'SYSTEM', '2014-07-11', null, null, null, null, null, null, (select ROUND_ID from STJ_ROUND where NAME = 'ROUND 2' and SEASON_ID = (select SEASON_ID from STJ_SEASON where YEAR = 2014))),
(now(), 'SYSTEM', now(), 'SYSTEM', '2014-07-18', null, null, null, null, null, null, (select ROUND_ID from STJ_ROUND where NAME = 'ROUND 2' and SEASON_ID = (select SEASON_ID from STJ_SEASON where YEAR = 2014))),
(now(), 'SYSTEM', now(), 'SYSTEM', '2014-07-25', null, null, null, null, null, null, (select ROUND_ID from STJ_ROUND where NAME = 'ROUND 2' and SEASON_ID = (select SEASON_ID from STJ_SEASON where YEAR = 2014))),
(now(), 'SYSTEM', now(), 'SYSTEM', '2014-08-01', null, null, null, null, null, null, (select ROUND_ID from STJ_ROUND where NAME = 'ROUND 2' and SEASON_ID = (select SEASON_ID from STJ_SEASON where YEAR = 2014))),
(now(), 'SYSTEM', now(), 'SYSTEM', '2014-08-08', null, null, null, null, null, null, (select ROUND_ID from STJ_ROUND where NAME = 'ROUND 2' and SEASON_ID = (select SEASON_ID from STJ_SEASON where YEAR = 2014))),
(now(), 'SYSTEM', now(), 'SYSTEM', '2014-08-15', null, null, null, null, null, null, (select ROUND_ID from STJ_ROUND where NAME = 'ROUND 2' and SEASON_ID = (select SEASON_ID from STJ_SEASON where YEAR = 2014))),
(now(), 'SYSTEM', now(), 'SYSTEM', '2014-08-22', null, null, null, null, null, null, (select ROUND_ID from STJ_ROUND where NAME = 'ROUND 2' and SEASON_ID = (select SEASON_ID from STJ_SEASON where YEAR = 2014))),
(now(), 'SYSTEM', now(), 'SYSTEM', '2014-08-29', null, null, null, null, null, null, (select ROUND_ID from STJ_ROUND where NAME = 'ROUND 2' and SEASON_ID = (select SEASON_ID from STJ_SEASON where YEAR = 2014))),
(now(), 'SYSTEM', now(), 'SYSTEM', '2014-09-05', null, null, null, null, null, null, (select ROUND_ID from STJ_ROUND where NAME = 'ROUND 2' and SEASON_ID = (select SEASON_ID from STJ_SEASON where YEAR = 2014))),
(now(), 'SYSTEM', now(), 'SYSTEM', '2014-09-12', null, null, null, null, null, null, (select ROUND_ID from STJ_ROUND where NAME = 'ROUND 2' and SEASON_ID = (select SEASON_ID from STJ_SEASON where YEAR = 2014)));

