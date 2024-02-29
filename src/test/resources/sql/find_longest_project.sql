select  id,
		max((DATE_PART('year', finish_date) - DATE_PART('year', start_date)) * 12 +
    	(DATE_PART('month', finish_date) - DATE_PART('month', start_date)))
from project
group by id
having max((DATE_PART('year', finish_date) - DATE_PART('year', start_date)) * 12 +
    (DATE_PART('month', finish_date) - DATE_PART('month', start_date)))  = 
    (
		select max(MOUNTH_COUNT) 
		from (
			select id,max((DATE_PART('year', finish_date) - DATE_PART('year', start_date)) * 12 +
    			(DATE_PART('month', finish_date) - DATE_PART('month', start_date))) as MOUNTH_COUNT 
    		from project 
			group by id 
			) as count_of_all_projects_in_months
);
