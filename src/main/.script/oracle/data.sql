-- 创建 employee 表
CREATE TABLE employee (
    id NUMBER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    first_name VARCHAR2(50) NOT NULL,
    last_name VARCHAR2(50) NOT NULL,
    email VARCHAR2(100),
    hire_date DATE,
    salary NUMBER(10, 2),
    department VARCHAR2(50)
);

-- 创建 employee_log 表
CREATE TABLE employee_log (
    log_id NUMBER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    employee_id NUMBER NOT NULL,
    action VARCHAR2(10) NOT NULL,
    timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);


INSERT INTO employee (first_name, last_name, email, hire_date, salary, department)
VALUES
  ('John', 'Doe', 'john.doe@example.com', TO_DATE('2022-01-01', 'YYYY-MM-DD'), 50000.00, 'IT'),
  ('Jane', 'Smith', 'jane.smith@example.com', TO_DATE('2022-02-15', 'YYYY-MM-DD'), 60000.00, 'HR'),
  ('Bob', 'Johnson', 'bob.johnson@example.com', TO_DATE('2022-03-20', 'YYYY-MM-DD'), 75000.00, 'Finance'),
  ('Alice', 'Williams', 'alice.williams@example.com', TO_DATE('2022-04-10', 'YYYY-MM-DD'), 55000.00, 'IT'),
  ('Charlie', 'Brown', 'charlie.brown@example.com', TO_DATE('2022-05-05', 'YYYY-MM-DD'), 70000.00, 'Marketing'),
  ('Eva', 'Lee', 'eva.lee@example.com', TO_DATE('2022-06-18', 'YYYY-MM-DD'), 80000.00, 'HR'),
  ('David', 'Clark', 'david.clark@example.com', TO_DATE('2022-07-23', 'YYYY-MM-DD'), 65000.00, 'Finance'),
  ('Grace', 'Anderson', 'grace.anderson@example.com', TO_DATE('2022-08-30', 'YYYY-MM-DD'), 60000.00, 'Marketing'),
  ('Sam', 'Taylor', 'sam.taylor@example.com', TO_DATE('2022-09-12', 'YYYY-MM-DD'), 72000.00, 'IT'),
  ('Olivia', 'Moore', 'olivia.moore@example.com', TO_DATE('2022-10-05', 'YYYY-MM-DD'), 68000.00, 'HR'),
  ('Teddy', 'Lai', 'teddy@example.com', TO_DATE('2022-10-05', 'YYYY-MM-DD'), 68000.00, 'HR');
