select name,salary
from worker
where salary in (
	select max(salary) 
	from worker
);