select p.id,
	(
	max((DATE_PART('year', finish_date) - DATE_PART('year', start_date)) * 12 +
    (DATE_PART('month', finish_date) - DATE_PART('month', start_date))) * (
    	select SUM from
			(
				select p2.id,sum(salary) from worker w  
				join project_worker pw on (w.id = pw.worker_id)
				join project p2 on (p2.id = pw.project_id)
				group by p2.id
			) as SUM
			where id = p.id
		)
	) as PRICE
from project p  
group by p.id
order by PRICE desc;