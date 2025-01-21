--IN PRODUCTION REMOVE COMMENT DO $$

-- 1. Unos Sveučilišta
INSERT INTO SVEUCILISTE (university_name)
VALUES ('Sveučilište u Zagrebu');

-- 2. Unos Fakulteta
WITH university_cte AS (
    SELECT university_id
    FROM SVEUCILISTE
    WHERE university_name = 'Sveučilište u Zagrebu'
)
INSERT INTO FAKULTET (faculty_name, faculty_university_university_id)
SELECT 'PMF', university_id FROM university_cte
UNION ALL
SELECT 'FER', university_id FROM university_cte
UNION ALL
SELECT 'FFZG', university_id FROM university_cte
UNION ALL
SELECT 'MEF', university_id FROM university_cte
UNION ALL
SELECT 'EFZG', university_id FROM university_cte;

-- 3. Unos Korisnika (including activity leaders)
INSERT INTO KORISNIK (
    first_name,
    last_name,
    email,
    password,
    date_of_registration,
    user_faculty_faculty_id,
    user_role
)
VALUES
    (
        'Marko',
        'Lukić',
        'marko.lukic@fm.unizg.hr',
        'some_password',
        '2024-06-01',
        (SELECT faculty_id FROM FAKULTET WHERE faculty_name = 'FFZG'),
        'ACTIVITY_LEADER'
    ),
    (
        'Ivan',
        'Horvat',
        'ivan.horvat@fz.unizg.hr',
        'hashed_password1',
        '2024-01-10',
        (SELECT faculty_id FROM FAKULTET WHERE faculty_name = 'PMF'),
        'TEACHER'
    ),
    (
        'Marija',
        'Kovač',
        'marija.kovac@ft.unizg.hr',
        'hashed_password2',
        '2024-02-15',
        (SELECT faculty_id FROM FAKULTET WHERE faculty_name = 'FER'),
        'TEACHER'
    ),
    (
        'Ana',
        'Babić',
        'ana.babic@fm.unizg.hr',
        'hashed_password3',
        '2024-03-20',
        (SELECT faculty_id FROM FAKULTET WHERE faculty_name = 'FFZG'),
        'STUDENT'
    ),
    (
        'Marko',
        'Novak',
        'marko.novak@fs.unizg.hr',
        'hashed_password4',
        '2024-04-25',
        (SELECT faculty_id FROM FAKULTET WHERE faculty_name = 'PMF'),
        'STUDENT'
    ),
    (
        'Petra',
        'Marić',
        'petra.maric@teh.unizg.hr',
        'hashed_password5',
        '2024-05-30',
        (SELECT faculty_id FROM FAKULTET WHERE faculty_name = 'FER'),
        'STUDENT'
    ),
    (
        'Daria',
        'Jurić',
        'daria.juric@ft.unizg.hr',
        'hashed_password8',
        '2024-08-18',
        (SELECT faculty_id FROM FAKULTET WHERE faculty_name = 'FER'),
        'ACTIVITY_LEADER'
    );

-- 4. Unos Nastavnika (TEACHER)
INSERT INTO TEACHER (user_id, profile_pictureurl, office_location)
VALUES
    (
        (SELECT user_id
         FROM KORISNIK
         WHERE email = 'ivan.horvat@fz.unizg.hr'),
            'http://example.com/images/ivan_horvat.jpg',
        'Ured 101, PMF'
    ),
    (
        (SELECT user_id
         FROM KORISNIK
         WHERE email = 'marija.kovac@ft.unizg.hr'),
        'http://example.com/images/marija_kovac.jpg',
        'Ured 202, FER'
    );

-- 5. Unos Tipova Aktivnosti
INSERT INTO TIP_AKTIVNOSTI (activity_type_name)
VALUES
    ('Rukomet'),
    ('Nogomet'),
    ('Kros'),
    ('Veslanje');

INSERT INTO LOKACIJA (location_name, address, city_code)
VALUES
    ('Glavna dvorana', 'Ulica sporta 5', '10000'),
    ('Laboratorij 1', 'Tehnička 12', '10001'),
    ('Seminarska sala A', 'Sveučilišna 3', '10002');

-- 6. Unos Activity Leader-a
INSERT INTO ACTIVITY_LEADER (
    user_id,
    profile_pictureurl,
    leader_activity_type_activity_type_id
)
SELECT
    user_id,
    'http://example.com/images/daria_juric.jpg',
    (SELECT activity_type_id
     FROM TIP_AKTIVNOSTI
     WHERE activity_type_name = 'Rukomet')
FROM KORISNIK
WHERE email = 'daria.juric@ft.unizg.hr';

INSERT INTO ACTIVITY_LEADER (
    user_id,
    profile_pictureurl,
    leader_activity_type_activity_type_id
)
SELECT
    user_id,
    'http://example.com/images/marko_lukic.jpg',
    (SELECT activity_type_id
     FROM TIP_AKTIVNOSTI
     WHERE activity_type_name = 'Nogomet')
FROM KORISNIK
WHERE email = 'marko.lukic@fm.unizg.hr';

-- 7. Unos Studenta (STUDENT)
INSERT INTO STUDENT (
    user_id,
    jmbag,
    academic_year,
    birth_date,
    gender,
    nationality,
    pass_status,
    phone_number,
    semester,
    total_points,
    student_teacher_user_id
)
SELECT
    user_id,
    'JMBAG' || user_id,
    '2023/2024',
    '2000-01-01',
    'M',
    'Croatian',
    true,
    '0987654321',
    1,
    0,
    (SELECT user_id FROM TEACHER LIMIT 1)
FROM KORISNIK
WHERE user_role = 'STUDENT';

-- 8. Unos Termina
INSERT INTO TERMIN (
    term_description,
    term_start,
    term_end,
    max_points,
    capacity,
    activity_type_term_activity_type_id,
    activity_leader_term_user_id,
    location_term_location_id
)
VALUES
    (
        'Trening rukomet',
        '2024-10-01 09:00:00',
        '2024-10-01 11:00:00',
        1,
        50,
        (SELECT activity_type_id
         FROM TIP_AKTIVNOSTI
         WHERE activity_type_name = 'Rukomet'),
        (SELECT user_id
         FROM KORISNIK
         WHERE email = 'daria.juric@ft.unizg.hr'),
        (SELECT location_id
         FROM LOKACIJA
         WHERE location_name = 'Glavna dvorana')
    ),
    (
        'Trening nogomet',
        '2024-10-02 13:00:00',
        '2024-10-02 16:00:00',
        1,
        30,
        (SELECT activity_type_id
         FROM TIP_AKTIVNOSTI
         WHERE activity_type_name = 'Nogomet'),
        (SELECT user_id
         FROM KORISNIK
         WHERE email = 'marko.lukic@fm.unizg.hr'),
        (SELECT location_id
         FROM LOKACIJA
         WHERE location_name = 'Laboratorij 1')
    );

-- 9. Prijave Termina
INSERT INTO PRIJAVLJUJE_TERMIN (
    student_id,
    termin_id,
    ostvareni_bodovi
)
VALUES
    (
        (SELECT user_id
         FROM KORISNIK
         WHERE email = 'ana.babic@fm.unizg.hr'),
        (SELECT term_id
         FROM TERMIN
         WHERE term_description = 'Trening rukomet'
            LIMIT 1),
    0
    ),
            (
                (SELECT user_id
                 FROM KORISNIK
                 WHERE email = 'marko.novak@fs.unizg.hr'),
                (SELECT term_id
                 FROM TERMIN
                 WHERE term_description = 'Trening rukomet'
                 LIMIT 1),
                0
            );


-- END $$;
