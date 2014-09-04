select w.week_id, w.wk_date, w.team_match_1_id, t1.team_nbr, t2.team_nbr,
w.team_match_2_id, t3.team_nbr, t4.team_nbr,
w.team_match_3_id, t5.team_nbr, t6.team_nbr,
w.team_match_4_id, t7.team_nbr, t8.team_nbr,
w.team_match_5_id, t9.team_nbr, t10.team_nbr,
w.team_match_6_id, t11.team_nbr, t12.team_nbr
from `STJ_WEEK` w
inner join `STJ_TEAM_MATCH` tm1 on tm1.team_match_id = w.team_match_1_id
inner join `STJ_TEAM_SCORE` ts1 on tm1.team_score_id_1 = ts1.team_score_id
inner join `STJ_TEAM_SCORE` ts2 on tm1.team_score_id_2 = ts2.team_score_id
inner join `STJ_TEAM` t1 on ts1.team_id = t1.team_id
inner join `STJ_TEAM` t2 on ts2.team_id = t2.team_id
inner join `STJ_TEAM_MATCH` tm2 on tm2.team_match_id = w.team_match_2_id
inner join `STJ_TEAM_SCORE` ts3 on tm2.team_score_id_1 = ts3.team_score_id
inner join `STJ_TEAM_SCORE` ts4 on tm2.team_score_id_2 = ts4.team_score_id
inner join `STJ_TEAM` t3 on ts3.team_id = t3.team_id
inner join `STJ_TEAM` t4 on ts4.team_id = t4.team_id
inner join `STJ_TEAM_MATCH` tm3 on tm3.team_match_id = w.team_match_3_id
inner join `STJ_TEAM_SCORE` ts5 on tm3.team_score_id_1 = ts5.team_score_id
inner join `STJ_TEAM_SCORE` ts6 on tm3.team_score_id_2 = ts6.team_score_id
inner join `STJ_TEAM` t5 on ts5.team_id = t5.team_id
inner join `STJ_TEAM` t6 on ts6.team_id = t6.team_id
inner join `STJ_TEAM_MATCH` tm4 on tm4.team_match_id = w.team_match_4_id
inner join `STJ_TEAM_SCORE` ts7 on tm4.team_score_id_1 = ts7.team_score_id
inner join `STJ_TEAM_SCORE` ts8 on tm4.team_score_id_2 = ts8.team_score_id
inner join `STJ_TEAM` t7 on ts7.team_id = t7.team_id
inner join `STJ_TEAM` t8 on ts8.team_id = t8.team_id
inner join `STJ_TEAM_MATCH` tm5 on tm5.team_match_id = w.team_match_5_id
inner join `STJ_TEAM_SCORE` ts9 on tm5.team_score_id_1 = ts9.team_score_id
inner join `STJ_TEAM_SCORE` ts10 on tm5.team_score_id_2 = ts10.team_score_id
inner join `STJ_TEAM` t9 on ts9.team_id = t9.team_id
inner join `STJ_TEAM` t10 on ts10.team_id = t10.team_id
inner join `STJ_TEAM_MATCH` tm6 on tm6.team_match_id = w.team_match_6_id
inner join `STJ_TEAM_SCORE` ts11 on tm6.team_score_id_1 = ts11.team_score_id
inner join `STJ_TEAM_SCORE` ts12 on tm6.team_score_id_2 = ts12.team_score_id
inner join `STJ_TEAM` t11 on ts11.team_id = t11.team_id
inner join `STJ_TEAM` t12 on ts12.team_id = t12.team_id
where w.wk_date > '2014-08-00'
order by w.wk_date

