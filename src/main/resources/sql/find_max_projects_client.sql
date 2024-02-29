select name, count(*) as count
from client c join project p  on (c.id = p.client_id)
group by name 
having count(*) = (
	select max(count) 
	from (
		select name,count(*) as count
		from client c join project p  on (c.id = p.client_id)
		group by name
		) as counting_projects_by_id
);