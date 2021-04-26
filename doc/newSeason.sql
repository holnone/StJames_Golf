INSERT INTO `STJ_SEASON` (`CRTN_DT`, `CRTN_USER_ID`, `LAST_UPTD_DT`, `LAST_UPTD_USER_ID`, `YEAR`) VALUES
(now(), 'SYSTEM', now(), 'SYSTEM', 2021);

INSERT INTO `STJ_ROUND` (`CRTN_DT`, `CRTN_USER_ID`, `LAST_UPTD_DT`, `LAST_UPTD_USER_ID`, `NAME`, `SEASON_ID`) VALUES
(now(), 'SYSTEM', now(), 'SYSTEM', 'ROUND 1', (select SEASON_ID from STJ_SEASON where YEAR = 2021)),
(now(), 'SYSTEM', now(), 'SYSTEM', 'ROUND 2', (select SEASON_ID from STJ_SEASON where YEAR = 2021));

INSERT INTO `STJ_SEASON_TEAM` (`SEASON_ID`, `TEAM_ID`) VALUES
((select SEASON_ID from STJ_SEASON where YEAR = 2021), 1),
((select SEASON_ID from STJ_SEASON where YEAR = 2021), 2),
((select SEASON_ID from STJ_SEASON where YEAR = 2021), 3),
((select SEASON_ID from STJ_SEASON where YEAR = 2021), 4),
((select SEASON_ID from STJ_SEASON where YEAR = 2021), 5),
((select SEASON_ID from STJ_SEASON where YEAR = 2021), 6),
((select SEASON_ID from STJ_SEASON where YEAR = 2021), 7),
((select SEASON_ID from STJ_SEASON where YEAR = 2021), 8),
((select SEASON_ID from STJ_SEASON where YEAR = 2021), 9),
((select SEASON_ID from STJ_SEASON where YEAR = 2021), 10),
((select SEASON_ID from STJ_SEASON where YEAR = 2021), 11),
((select SEASON_ID from STJ_SEASON where YEAR = 2021), 12);


INSERT INTO `STJ_WEEK` (`CRTN_DT`, `CRTN_USER_ID`, `LAST_UPTD_DT`, `LAST_UPTD_USER_ID`, `WK_DATE`, `TEAM_MATCH_1_ID`, `TEAM_MATCH_2_ID`, `TEAM_MATCH_3_ID`, `TEAM_MATCH_4_ID`, `TEAM_MATCH_5_ID`, `TEAM_MATCH_6_ID`, `ROUND_ID`) VALUES
(now(), 'SYSTEM', now(), 'SYSTEM', '2021-04-09', null, null, null, null, null, null, (select ROUND_ID from STJ_ROUND where NAME = 'ROUND 1' and SEASON_ID = (select SEASON_ID from STJ_SEASON where YEAR = 2021))),
(now(), 'SYSTEM', now(), 'SYSTEM', '2021-04-16', null, null, null, null, null, null, (select ROUND_ID from STJ_ROUND where NAME = 'ROUND 1' and SEASON_ID = (select SEASON_ID from STJ_SEASON where YEAR = 2021))),
(now(), 'SYSTEM', now(), 'SYSTEM', '2021-04-23', null, null, null, null, null, null, (select ROUND_ID from STJ_ROUND where NAME = 'ROUND 1' and SEASON_ID = (select SEASON_ID from STJ_SEASON where YEAR = 2021))),
(now(), 'SYSTEM', now(), 'SYSTEM', '2021-04-30', null, null, null, null, null, null, (select ROUND_ID from STJ_ROUND where NAME = 'ROUND 1' and SEASON_ID = (select SEASON_ID from STJ_SEASON where YEAR = 2021))),
(now(), 'SYSTEM', now(), 'SYSTEM', '2021-05-07', null, null, null, null, null, null, (select ROUND_ID from STJ_ROUND where NAME = 'ROUND 1' and SEASON_ID = (select SEASON_ID from STJ_SEASON where YEAR = 2021))),
(now(), 'SYSTEM', now(), 'SYSTEM', '2021-05-14', null, null, null, null, null, null, (select ROUND_ID from STJ_ROUND where NAME = 'ROUND 1' and SEASON_ID = (select SEASON_ID from STJ_SEASON where YEAR = 2021))),
(now(), 'SYSTEM', now(), 'SYSTEM', '2021-05-21', null, null, null, null, null, null, (select ROUND_ID from STJ_ROUND where NAME = 'ROUND 1' and SEASON_ID = (select SEASON_ID from STJ_SEASON where YEAR = 2021))),
(now(), 'SYSTEM', now(), 'SYSTEM', '2021-05-28', null, null, null, null, null, null, (select ROUND_ID from STJ_ROUND where NAME = 'ROUND 1' and SEASON_ID = (select SEASON_ID from STJ_SEASON where YEAR = 2021))),
(now(), 'SYSTEM', now(), 'SYSTEM', '2021-06-04', null, null, null, null, null, null, (select ROUND_ID from STJ_ROUND where NAME = 'ROUND 1' and SEASON_ID = (select SEASON_ID from STJ_SEASON where YEAR = 2021))),
(now(), 'SYSTEM', now(), 'SYSTEM', '2021-06-11', null, null, null, null, null, null, (select ROUND_ID from STJ_ROUND where NAME = 'ROUND 1' and SEASON_ID = (select SEASON_ID from STJ_SEASON where YEAR = 2021))),
(now(), 'SYSTEM', now(), 'SYSTEM', '2021-06-18', null, null, null, null, null, null, (select ROUND_ID from STJ_ROUND where NAME = 'ROUND 1' and SEASON_ID = (select SEASON_ID from STJ_SEASON where YEAR = 2021))),
(now(), 'SYSTEM', now(), 'SYSTEM', '2021-06-25', null, null, null, null, null, null, (select ROUND_ID from STJ_ROUND where NAME = 'ROUND 2' and SEASON_ID = (select SEASON_ID from STJ_SEASON where YEAR = 2021))),
(now(), 'SYSTEM', now(), 'SYSTEM', '2021-07-09', null, null, null, null, null, null, (select ROUND_ID from STJ_ROUND where NAME = 'ROUND 2' and SEASON_ID = (select SEASON_ID from STJ_SEASON where YEAR = 2021))),
(now(), 'SYSTEM', now(), 'SYSTEM', '2021-07-16', null, null, null, null, null, null, (select ROUND_ID from STJ_ROUND where NAME = 'ROUND 2' and SEASON_ID = (select SEASON_ID from STJ_SEASON where YEAR = 2021))),
(now(), 'SYSTEM', now(), 'SYSTEM', '2021-07-23', null, null, null, null, null, null, (select ROUND_ID from STJ_ROUND where NAME = 'ROUND 2' and SEASON_ID = (select SEASON_ID from STJ_SEASON where YEAR = 2021))),
(now(), 'SYSTEM', now(), 'SYSTEM', '2021-07-30', null, null, null, null, null, null, (select ROUND_ID from STJ_ROUND where NAME = 'ROUND 2' and SEASON_ID = (select SEASON_ID from STJ_SEASON where YEAR = 2021))),
(now(), 'SYSTEM', now(), 'SYSTEM', '2021-08-06', null, null, null, null, null, null, (select ROUND_ID from STJ_ROUND where NAME = 'ROUND 2' and SEASON_ID = (select SEASON_ID from STJ_SEASON where YEAR = 2021))),
(now(), 'SYSTEM', now(), 'SYSTEM', '2021-08-13', null, null, null, null, null, null, (select ROUND_ID from STJ_ROUND where NAME = 'ROUND 2' and SEASON_ID = (select SEASON_ID from STJ_SEASON where YEAR = 2021))),
(now(), 'SYSTEM', now(), 'SYSTEM', '2021-08-20', null, null, null, null, null, null, (select ROUND_ID from STJ_ROUND where NAME = 'ROUND 2' and SEASON_ID = (select SEASON_ID from STJ_SEASON where YEAR = 2021))),
(now(), 'SYSTEM', now(), 'SYSTEM', '2021-08-27', null, null, null, null, null, null, (select ROUND_ID from STJ_ROUND where NAME = 'ROUND 2' and SEASON_ID = (select SEASON_ID from STJ_SEASON where YEAR = 2021))),
(now(), 'SYSTEM', now(), 'SYSTEM', '2021-09-03', null, null, null, null, null, null, (select ROUND_ID from STJ_ROUND where NAME = 'ROUND 2' and SEASON_ID = (select SEASON_ID from STJ_SEASON where YEAR = 2021))),
(now(), 'SYSTEM', now(), 'SYSTEM', '2021-09-10', null, null, null, null, null, null, (select ROUND_ID from STJ_ROUND where NAME = 'ROUND 2' and SEASON_ID = (select SEASON_ID from STJ_SEASON where YEAR = 2021)));
