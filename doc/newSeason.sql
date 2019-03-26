INSERT INTO `STJ_SEASON` (`CRTN_DT`, `CRTN_USER_ID`, `LAST_UPTD_DT`, `LAST_UPTD_USER_ID`, `YEAR`) VALUES
(now(), 'SYSTEM', now(), 'SYSTEM', 2019);

INSERT INTO `STJ_ROUND` (`CRTN_DT`, `CRTN_USER_ID`, `LAST_UPTD_DT`, `LAST_UPTD_USER_ID`, `NAME`, `SEASON_ID`) VALUES
(now(), 'SYSTEM', now(), 'SYSTEM', 'ROUND 1', (select SEASON_ID from STJ_SEASON where YEAR = 2019)),
(now(), 'SYSTEM', now(), 'SYSTEM', 'ROUND 2', (select SEASON_ID from STJ_SEASON where YEAR = 2019));

INSERT INTO `STJ_SEASON_TEAM` (`SEASON_ID`, `TEAM_ID`) VALUES
((select SEASON_ID from STJ_SEASON where YEAR = 2019), 1),
((select SEASON_ID from STJ_SEASON where YEAR = 2019), 2),
((select SEASON_ID from STJ_SEASON where YEAR = 2019), 3),
((select SEASON_ID from STJ_SEASON where YEAR = 2019), 4),
((select SEASON_ID from STJ_SEASON where YEAR = 2019), 5),
((select SEASON_ID from STJ_SEASON where YEAR = 2019), 6),
((select SEASON_ID from STJ_SEASON where YEAR = 2019), 7),
((select SEASON_ID from STJ_SEASON where YEAR = 2019), 8),
((select SEASON_ID from STJ_SEASON where YEAR = 2019), 9),
((select SEASON_ID from STJ_SEASON where YEAR = 2019), 10),
((select SEASON_ID from STJ_SEASON where YEAR = 2019), 11),
((select SEASON_ID from STJ_SEASON where YEAR = 2019), 12);


INSERT INTO `STJ_WEEK` (`CRTN_DT`, `CRTN_USER_ID`, `LAST_UPTD_DT`, `LAST_UPTD_USER_ID`, `WK_DATE`, `TEAM_MATCH_1_ID`, `TEAM_MATCH_2_ID`, `TEAM_MATCH_3_ID`, `TEAM_MATCH_4_ID`, `TEAM_MATCH_5_ID`, `TEAM_MATCH_6_ID`, `ROUND_ID`) VALUES
(now(), 'SYSTEM', now(), 'SYSTEM', '2019-04-05', null, null, null, null, null, null, (select ROUND_ID from STJ_ROUND where NAME = 'ROUND 1' and SEASON_ID = (select SEASON_ID from STJ_SEASON where YEAR = 2019))),
(now(), 'SYSTEM', now(), 'SYSTEM', '2019-04-12', null, null, null, null, null, null, (select ROUND_ID from STJ_ROUND where NAME = 'ROUND 1' and SEASON_ID = (select SEASON_ID from STJ_SEASON where YEAR = 2019))),
(now(), 'SYSTEM', now(), 'SYSTEM', '2019-04-19', null, null, null, null, null, null, (select ROUND_ID from STJ_ROUND where NAME = 'ROUND 1' and SEASON_ID = (select SEASON_ID from STJ_SEASON where YEAR = 2019))),
(now(), 'SYSTEM', now(), 'SYSTEM', '2019-04-26', null, null, null, null, null, null, (select ROUND_ID from STJ_ROUND where NAME = 'ROUND 1' and SEASON_ID = (select SEASON_ID from STJ_SEASON where YEAR = 2019))),
(now(), 'SYSTEM', now(), 'SYSTEM', '2019-05-03', null, null, null, null, null, null, (select ROUND_ID from STJ_ROUND where NAME = 'ROUND 1' and SEASON_ID = (select SEASON_ID from STJ_SEASON where YEAR = 2019))),
(now(), 'SYSTEM', now(), 'SYSTEM', '2019-05-10', null, null, null, null, null, null, (select ROUND_ID from STJ_ROUND where NAME = 'ROUND 1' and SEASON_ID = (select SEASON_ID from STJ_SEASON where YEAR = 2019))),
(now(), 'SYSTEM', now(), 'SYSTEM', '2019-05-17', null, null, null, null, null, null, (select ROUND_ID from STJ_ROUND where NAME = 'ROUND 1' and SEASON_ID = (select SEASON_ID from STJ_SEASON where YEAR = 2019))),
(now(), 'SYSTEM', now(), 'SYSTEM', '2019-05-24', null, null, null, null, null, null, (select ROUND_ID from STJ_ROUND where NAME = 'ROUND 1' and SEASON_ID = (select SEASON_ID from STJ_SEASON where YEAR = 2019))),
(now(), 'SYSTEM', now(), 'SYSTEM', '2019-05-31', null, null, null, null, null, null, (select ROUND_ID from STJ_ROUND where NAME = 'ROUND 1' and SEASON_ID = (select SEASON_ID from STJ_SEASON where YEAR = 2019))),
(now(), 'SYSTEM', now(), 'SYSTEM', '2019-06-07', null, null, null, null, null, null, (select ROUND_ID from STJ_ROUND where NAME = 'ROUND 1' and SEASON_ID = (select SEASON_ID from STJ_SEASON where YEAR = 2019))),
(now(), 'SYSTEM', now(), 'SYSTEM', '2019-06-14', null, null, null, null, null, null, (select ROUND_ID from STJ_ROUND where NAME = 'ROUND 1' and SEASON_ID = (select SEASON_ID from STJ_SEASON where YEAR = 2019))),
(now(), 'SYSTEM', now(), 'SYSTEM', '2019-06-21', null, null, null, null, null, null, (select ROUND_ID from STJ_ROUND where NAME = 'ROUND 2' and SEASON_ID = (select SEASON_ID from STJ_SEASON where YEAR = 2019))),
(now(), 'SYSTEM', now(), 'SYSTEM', '2019-06-28', null, null, null, null, null, null, (select ROUND_ID from STJ_ROUND where NAME = 'ROUND 2' and SEASON_ID = (select SEASON_ID from STJ_SEASON where YEAR = 2019))),
(now(), 'SYSTEM', now(), 'SYSTEM', '2019-07-05', null, null, null, null, null, null, (select ROUND_ID from STJ_ROUND where NAME = 'ROUND 2' and SEASON_ID = (select SEASON_ID from STJ_SEASON where YEAR = 2019))),
(now(), 'SYSTEM', now(), 'SYSTEM', '2019-07-12', null, null, null, null, null, null, (select ROUND_ID from STJ_ROUND where NAME = 'ROUND 2' and SEASON_ID = (select SEASON_ID from STJ_SEASON where YEAR = 2019))),
(now(), 'SYSTEM', now(), 'SYSTEM', '2019-07-19', null, null, null, null, null, null, (select ROUND_ID from STJ_ROUND where NAME = 'ROUND 2' and SEASON_ID = (select SEASON_ID from STJ_SEASON where YEAR = 2019))),
(now(), 'SYSTEM', now(), 'SYSTEM', '2019-07-26', null, null, null, null, null, null, (select ROUND_ID from STJ_ROUND where NAME = 'ROUND 2' and SEASON_ID = (select SEASON_ID from STJ_SEASON where YEAR = 2019))),
(now(), 'SYSTEM', now(), 'SYSTEM', '2019-08-02', null, null, null, null, null, null, (select ROUND_ID from STJ_ROUND where NAME = 'ROUND 2' and SEASON_ID = (select SEASON_ID from STJ_SEASON where YEAR = 2019))),
(now(), 'SYSTEM', now(), 'SYSTEM', '2019-08-09', null, null, null, null, null, null, (select ROUND_ID from STJ_ROUND where NAME = 'ROUND 2' and SEASON_ID = (select SEASON_ID from STJ_SEASON where YEAR = 2019))),
(now(), 'SYSTEM', now(), 'SYSTEM', '2019-08-16', null, null, null, null, null, null, (select ROUND_ID from STJ_ROUND where NAME = 'ROUND 2' and SEASON_ID = (select SEASON_ID from STJ_SEASON where YEAR = 2019))),
(now(), 'SYSTEM', now(), 'SYSTEM', '2019-08-23', null, null, null, null, null, null, (select ROUND_ID from STJ_ROUND where NAME = 'ROUND 2' and SEASON_ID = (select SEASON_ID from STJ_SEASON where YEAR = 2019))),
(now(), 'SYSTEM', now(), 'SYSTEM', '2019-08-30', null, null, null, null, null, null, (select ROUND_ID from STJ_ROUND where NAME = 'ROUND 2' and SEASON_ID = (select SEASON_ID from STJ_SEASON where YEAR = 2019)));
