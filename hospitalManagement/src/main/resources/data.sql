INSERT INTO patient(name,gender,email,birth_date,blood_group)
VALUES
    ('Amit Sharma', 'Male', 'amit.sharma@gmail.com', '1999-05-12', 'O_POSITIVE'),
    ('Neha Verma', 'Female', 'neha.verma@gmail.com', '2000-08-25', 'A_POSITIVE'),
    ('Rahul Singh', 'Male', 'rahul.singh@gmail.com', '1998-12-03', 'B_POSITIVE'),
    ('Pooja Patel', 'Female', 'pooja.patel@gmail.com', '2001-03-18', 'AB_POSITIVE'),
    ('Karan Mehta', 'Male', 'karan.mehta@gmail.com', '1997-07-09', 'O_NEGATIVE'),
    ('Sneha Iyer', 'Female', 'sneha.iyer@gmail.com', '2002-11-21', 'A_NEGATIVE');

INSERT INTO doctor(name,specialisation,email)

    VALUES
        ('Dr.Rakesh Mehta','Cardiology','metha.rakesh@gmail.com'),
        ('Dr.Hunuman Pandey','Dermatology','hauman.pandey@gmail.com'),
        ('Dr.Arjun Nair','Orthopedics','Arjun.nair@gmail.com');

INSERT INTO appointment(appointment_time,reason,doctor_id,patient_id)

VALUES
    ('2026-01-30 10:00:00', 'Fever and cold', 1, 1),
    ('2026-01-30 11:30:00', 'Skin allergy', 2, 2),
    ('2026-01-31 09:00:00', 'Back pain', 3, 3),
    ('2026-02-01 14:00:00', 'Regular checkup', 1, 4),
    ('2026-02-14 10:30:00','Shoulders pain',3,2),
    ('2026-02-02 16:15:00', 'Headache issue', 2, 5);