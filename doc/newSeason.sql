INSERT INTO `STJ_SEASON` (`CRTN_DT`, `CRTN_USER_ID`, `LAST_UPTD_DT`, `LAST_UPTD_USER_ID`, `YEAR`) VALUES
(now(), 'SYSTEM', now(), 'SYSTEM', 2020);

INSERT INTO `STJ_ROUND` (`CRTN_DT`, `CRTN_USER_ID`, `LAST_UPTD_DT`, `LAST_UPTD_USER_ID`, `NAME`, `SEASON_ID`) VALUES
(now(), 'SYSTEM', now(), 'SYSTEM', 'ROUND 1', (select SEASON_ID from STJ_SEASON where YEAR = 2020)),
(now(), 'SYSTEM', now(), 'SYSTEM', 'ROUND 2', (select SEASON_ID from STJ_SEASON where YEAR = 2020));

INSERT INTO `STJ_SEASON_TEAM` (`SEASON_ID`, `TEAM_ID`) VALUES
((select SEASON_ID from STJ_SEASON where YEAR = 2020), 1),
((select SEASON_ID from STJ_SEASON where YEAR = 2020), 2),
((select SEASON_ID from STJ_SEASON where YEAR = 2020), 3),
((select SEASON_ID from STJ_SEASON where YEAR = 2020), 4),
((select SEASON_ID from STJ_SEASON where YEAR = 2020), 5),
((select SEASON_ID from STJ_SEASON where YEAR = 2020), 6),
((select SEASON_ID from STJ_SEASON where YEAR = 2020), 7),
((select SEASON_ID from STJ_SEASON where YEAR = 2020), 8),
((select SEASON_ID from STJ_SEASON where YEAR = 2020), 9),
((select SEASON_ID from STJ_SEASON where YEAR = 2020), 10),
((select SEASON_ID from STJ_SEASON where YEAR = 2020), 11),
((select SEASON_ID from STJ_SEASON where YEAR = 2020), 12);


INSERT INTO `STJ_WEEK` (`CRTN_DT`, `CRTN_USER_ID`, `LAST_UPTD_DT`, `LAST_UPTD_USER_ID`, `WK_DATE`, `TEAM_MATCH_1_ID`, `TEAM_MATCH_2_ID`, `TEAM_MATCH_3_ID`, `TEAM_MATCH_4_ID`, `TEAM_MATCH_5_ID`, `TEAM_MATCH_6_ID`, `ROUND_ID`) VALUES
(now(), 'SYSTEM', now(), 'SYSTEM', '2020-04-03', null, null, null, null, null, null, (select ROUND_ID from STJ_ROUND where NAME = 'ROUND 1' and SEASON_ID = (select SEASON_ID from STJ_SEASON where YEAR = 2020))),
(now(), 'SYSTEM', now(), 'SYSTEM', '2020-04-10', null, null, null, null, null, null, (select ROUND_ID from STJ_ROUND where NAME = 'ROUND 1' and SEASON_ID = (select SEASON_ID from STJ_SEASON where YEAR = 2020))),
(now(), 'SYSTEM', now(), 'SYSTEM', '2020-04-17', null, null, null, null, null, null, (select ROUND_ID from STJ_ROUND where NAME = 'ROUND 1' and SEASON_ID = (select SEASON_ID from STJ_SEASON where YEAR = 2020))),
(now(), 'SYSTEM', now(), 'SYSTEM', '2020-04-24', null, null, null, null, null, null, (select ROUND_ID from STJ_ROUND where NAME = 'ROUND 1' and SEASON_ID = (select SEASON_ID from STJ_SEASON where YEAR = 2020))),
(now(), 'SYSTEM', now(), 'SYSTEM', '2020-05-01', null, null, null, null, null, null, (select ROUND_ID from STJ_ROUND where NAME = 'ROUND 1' and SEASON_ID = (select SEASON_ID from STJ_SEASON where YEAR = 2020))),
(now(), 'SYSTEM', now(), 'SYSTEM', '2020-05-08', null, null, null, null, null, null, (select ROUND_ID from STJ_ROUND where NAME = 'ROUND 1' and SEASON_ID = (select SEASON_ID from STJ_SEASON where YEAR = 2020))),
(now(), 'SYSTEM', now(), 'SYSTEM', '2020-05-15', null, null, null, null, null, null, (select ROUND_ID from STJ_ROUND where NAME = 'ROUND 1' and SEASON_ID = (select SEASON_ID from STJ_SEASON where YEAR = 2020))),
(now(), 'SYSTEM', now(), 'SYSTEM', '2020-05-22', null, null, null, null, null, null, (select ROUND_ID from STJ_ROUND where NAME = 'ROUND 1' and SEASON_ID = (select SEASON_ID from STJ_SEASON where YEAR = 2020))),
(now(), 'SYSTEM', now(), 'SYSTEM', '2020-05-29', null, null, null, null, null, null, (select ROUND_ID from STJ_ROUND where NAME = 'ROUND 1' and SEASON_ID = (select SEASON_ID from STJ_SEASON where YEAR = 2020))),
(now(), 'SYSTEM', now(), 'SYSTEM', '2020-06-05', null, null, null, null, null, null, (select ROUND_ID from STJ_ROUND where NAME = 'ROUND 1' and SEASON_ID = (select SEASON_ID from STJ_SEASON where YEAR = 2020))),
(now(), 'SYSTEM', now(), 'SYSTEM', '2020-06-12', null, null, null, null, null, null, (select ROUND_ID from STJ_ROUND where NAME = 'ROUND 1' and SEASON_ID = (select SEASON_ID from STJ_SEASON where YEAR = 2020))),
(now(), 'SYSTEM', now(), 'SYSTEM', '2020-06-19', null, null, null, null, null, null, (select ROUND_ID from STJ_ROUND where NAME = 'ROUND 2' and SEASON_ID = (select SEASON_ID from STJ_SEASON where YEAR = 2020))),
(now(), 'SYSTEM', now(), 'SYSTEM', '2020-06-26', null, null, null, null, null, null, (select ROUND_ID from STJ_ROUND where NAME = 'ROUND 2' and SEASON_ID = (select SEASON_ID from STJ_SEASON where YEAR = 2020))),
(now(), 'SYSTEM', now(), 'SYSTEM', '2020-07-03', null, null, null, null, null, null, (select ROUND_ID from STJ_ROUND where NAME = 'ROUND 2' and SEASON_ID = (select SEASON_ID from STJ_SEASON where YEAR = 2020))),
(now(), 'SYSTEM', now(), 'SYSTEM', '2020-07-10', null, null, null, null, null, null, (select ROUND_ID from STJ_ROUND where NAME = 'ROUND 2' and SEASON_ID = (select SEASON_ID from STJ_SEASON where YEAR = 2020))),
(now(), 'SYSTEM', now(), 'SYSTEM', '2020-07-17', null, null, null, null, null, null, (select ROUND_ID from STJ_ROUND where NAME = 'ROUND 2' and SEASON_ID = (select SEASON_ID from STJ_SEASON where YEAR = 2020))),
(now(), 'SYSTEM', now(), 'SYSTEM', '2020-07-24', null, null, null, null, null, null, (select ROUND_ID from STJ_ROUND where NAME = 'ROUND 2' and SEASON_ID = (select SEASON_ID from STJ_SEASON where YEAR = 2020))),
(now(), 'SYSTEM', now(), 'SYSTEM', '2020-07-31', null, null, null, null, null, null, (select ROUND_ID from STJ_ROUND where NAME = 'ROUND 2' and SEASON_ID = (select SEASON_ID from STJ_SEASON where YEAR = 2020))),
(now(), 'SYSTEM', now(), 'SYSTEM', '2020-08-07', null, null, null, null, null, null, (select ROUND_ID from STJ_ROUND where NAME = 'ROUND 2' and SEASON_ID = (select SEASON_ID from STJ_SEASON where YEAR = 2020))),
(now(), 'SYSTEM', now(), 'SYSTEM', '2020-08-14', null, null, null, null, null, null, (select ROUND_ID from STJ_ROUND where NAME = 'ROUND 2' and SEASON_ID = (select SEASON_ID from STJ_SEASON where YEAR = 2020))),
(now(), 'SYSTEM', now(), 'SYSTEM', '2020-08-21', null, null, null, null, null, null, (select ROUND_ID from STJ_ROUND where NAME = 'ROUND 2' and SEASON_ID = (select SEASON_ID from STJ_SEASON where YEAR = 2020))),
(now(), 'SYSTEM', now(), 'SYSTEM', '2020-08-28', null, null, null, null, null, null, (select ROUND_ID from STJ_ROUND where NAME = 'ROUND 2' and SEASON_ID = (select SEASON_ID from STJ_SEASON where YEAR = 2020)));
