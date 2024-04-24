--create test table
CREATE TABLE employee (
    id INT AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    email VARCHAR(100),
    hire_date DATE,
    salary DECIMAL(10, 2),
    department VARCHAR(50)
);

CREATE TABLE employee_log (
    log_id INT AUTO_INCREMENT PRIMARY KEY,
    employee_id INT NOT NULL,
    first_name VARCHAR(50) NOT NULL,
    action VARCHAR(10) NOT NULL,
    timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);


-- select
SELECT * FROM test.employee;
SELECT * FROM employee_log;



--create test trigger
DELIMITER $$
    CREATE TRIGGER employee_audit
    AFTER INSERT ON employee
        FOR EACH ROW
    BEGIN
        DECLARE action VARCHAR(10);
            SET action = 'INSERT';

    INSERT INTO employee_log (employee_id, first_name ,action, timestamp)
    VALUES (NEW.id, New.first_name ,action, NOW());
    END;
$$
DELIMITER ;


SHOW TRIGGERS LIKE '%employee%';

DROP TRIGGER IF EXISTS employee_audit;



--test
INSERT INTO employee (first_name, last_name, email, hire_date, salary, department)
VALUES
  ('John', 'Doe', 'john.doe@example.com', '2022-01-01', 50000.00, 'IT'),
  ('Jane', 'Smith', 'jane.smith@example.com', '2022-02-15', 60000.00, 'HR'),
  ('Bob', 'Johnson', 'bob.johnson@example.com', '2022-03-20', 75000.00, 'Finance'),
  ('Alice', 'Williams', 'alice.williams@example.com', '2022-04-10', 55000.00, 'IT'),
  ('Charlie', 'Brown', 'charlie.brown@example.com', '2022-05-05', 70000.00, 'Marketing'),
  ('Eva', 'Lee', 'eva.lee@example.com', '2022-06-18', 80000.00, 'HR'),
  ('David', 'Clark', 'david.clark@example.com', '2022-07-23', 65000.00, 'Finance'),
  ('Grace', 'Anderson', 'grace.anderson@example.com', '2022-08-30', 60000.00, 'Marketing'),
  ('Sam', 'Taylor', 'sam.taylor@example.com', '2022-09-12', 72000.00, 'IT'),
  ('Olivia', 'Moore', 'olivia.moore@example.com', '2022-10-05', 68000.00, 'HR'),
  ('Teddy', 'Lai', 'teddy@example.com', '2023-10-04', 50000.00, 'HR');



delete from employee where first_name like '%Teddy%' ;







