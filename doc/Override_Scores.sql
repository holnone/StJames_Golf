select WEEK_ID,WK_DATE, TEAM_MATCH_1_ID, TEAM_MATCH_2_ID, TEAM_MATCH_5_ID, TEAM_MATCH_3_ID, TEAM_MATCH_4_ID, TEAM_MATCH_6_ID from `STJ_WEEK` where wk_date > '2017-01-01';

--ROUND 1
select * from `STJ_TEAM_MATCH` where team_match_id in (911,920,925,931,937,944,950,955,962,907,967);
update `STJ_TEAM_MATCH` set override = 'Y' where team_match_id in (911,920,925,931,937,944,950,955,962,907,967);

select * from `STJ_TEAM_SCORE` where team_score_id in (1821,1822);
update `STJ_TEAM_SCORE` set override_score = 10 where team_score_id = 1821;
update `STJ_TEAM_SCORE` set override_score = 0 where team_score_id = 1822;
select * from `STJ_TEAM_SCORE` where team_score_id in (1839,1840);
update `STJ_TEAM_SCORE` set override_score = 10 where team_score_id = 1839;
update `STJ_TEAM_SCORE` set override_score = 0 where team_score_id = 1840;
select * from `STJ_TEAM_SCORE` where team_score_id in (1849,1850);
update `STJ_TEAM_SCORE` set override_score = 10 where team_score_id = 1850;
update `STJ_TEAM_SCORE` set override_score = 0 where team_score_id = 1849;
select * from `STJ_TEAM_SCORE` where team_score_id in (1861,1862);
update `STJ_TEAM_SCORE` set override_score = 10 where team_score_id = 1861;
update `STJ_TEAM_SCORE` set override_score = 0 where team_score_id = 1862;
select * from `STJ_TEAM_SCORE` where team_score_id in (1873,1874);
update `STJ_TEAM_SCORE` set override_score = 10 where team_score_id = 1873;
update `STJ_TEAM_SCORE` set override_score = 0 where team_score_id = 1874;
select * from `STJ_TEAM_SCORE` where team_score_id in (1887,1888);
update `STJ_TEAM_SCORE` set override_score = 10 where team_score_id = 1888;
update `STJ_TEAM_SCORE` set override_score = 0 where team_score_id = 1887;
select * from `STJ_TEAM_SCORE` where team_score_id in (1899,1900);
update `STJ_TEAM_SCORE` set override_score = 10 where team_score_id = 1900;
update `STJ_TEAM_SCORE` set override_score = 0 where team_score_id = 1899;
select * from `STJ_TEAM_SCORE` where team_score_id in (1909,1910);
update `STJ_TEAM_SCORE` set override_score = 10 where team_score_id = 1909;
update `STJ_TEAM_SCORE` set override_score = 0 where team_score_id = 1910;
select * from `STJ_TEAM_SCORE` where team_score_id in (1923,1924);
update `STJ_TEAM_SCORE` set override_score = 10 where team_score_id = 1923;
update `STJ_TEAM_SCORE` set override_score = 0 where team_score_id = 1924;
select * from `STJ_TEAM_SCORE` where team_score_id in (1813,1814);
update `STJ_TEAM_SCORE` set override_score = 10 where team_score_id = 1814;
update `STJ_TEAM_SCORE` set override_score = 0 where team_score_id = 1813;
select * from `STJ_TEAM_SCORE` where team_score_id in (1933,1934);
update `STJ_TEAM_SCORE` set override_score = 10 where team_score_id = 1933;
update `STJ_TEAM_SCORE` set override_score = 0 where team_score_id = 1934;


--ROUND 2
select * from `STJ_TEAM_MATCH` where team_match_id in (973,980,991,998,1004,1009,1015,1022,1028,1034,985);
update `STJ_TEAM_MATCH` set override = 'Y' where team_match_id in (973,980,991,998,1004,1009,1015,1022,1028,1034,985);

select * from `STJ_TEAM_SCORE` where team_score_id in (1945,1946);
update `STJ_TEAM_SCORE` set override_score = 10 where team_score_id = 1946;
update `STJ_TEAM_SCORE` set override_score = 0 where team_score_id = 1945;
select * from `STJ_TEAM_SCORE` where team_score_id in (1959,1960);
update `STJ_TEAM_SCORE` set override_score = 10 where team_score_id = 1959;
update `STJ_TEAM_SCORE` set override_score = 0 where team_score_id = 1960;
select * from `STJ_TEAM_SCORE` where team_score_id in (1981,1982);
update `STJ_TEAM_SCORE` set override_score = 10 where team_score_id = 1981;
update `STJ_TEAM_SCORE` set override_score = 0 where team_score_id = 1982;
select * from `STJ_TEAM_SCORE` where team_score_id in (1995,1996);
update `STJ_TEAM_SCORE` set override_score = 10 where team_score_id = 1995;
update `STJ_TEAM_SCORE` set override_score = 0 where team_score_id = 1996;
select * from `STJ_TEAM_SCORE` where team_score_id in (2007,2008);
update `STJ_TEAM_SCORE` set override_score = 10 where team_score_id = 2008;
update `STJ_TEAM_SCORE` set override_score = 0 where team_score_id = 2007;
select * from `STJ_TEAM_SCORE` where team_score_id in (2017,2018);
update `STJ_TEAM_SCORE` set override_score = 10 where team_score_id = 2017;
update `STJ_TEAM_SCORE` set override_score = 0 where team_score_id = 2018;
select * from `STJ_TEAM_SCORE` where team_score_id in (2029,2030);
update `STJ_TEAM_SCORE` set override_score = 10 where team_score_id = 2029;
update `STJ_TEAM_SCORE` set override_score = 0 where team_score_id = 2030;
select * from `STJ_TEAM_SCORE` where team_score_id in (2043,2044);
update `STJ_TEAM_SCORE` set override_score = 10 where team_score_id = 2044;
update `STJ_TEAM_SCORE` set override_score = 0 where team_score_id = 2043;
select * from `STJ_TEAM_SCORE` where team_score_id in (2055,2056);
update `STJ_TEAM_SCORE` set override_score = 10 where team_score_id = 2055;
update `STJ_TEAM_SCORE` set override_score = 0 where team_score_id = 2056;
select * from `STJ_TEAM_SCORE` where team_score_id in (2067,2068);
update `STJ_TEAM_SCORE` set override_score = 10 where team_score_id = 2067;
update `STJ_TEAM_SCORE` set override_score = 0 where team_score_id = 2068;
select * from `STJ_TEAM_SCORE` where team_score_id in (1969,1970);
update `STJ_TEAM_SCORE` set override_score = 10 where team_score_id = 1970;
update `STJ_TEAM_SCORE` set override_score = 0 where team_score_id = 1969;
