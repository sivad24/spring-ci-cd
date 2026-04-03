CREATE TABLE IF NOT EXISTS users (
                                     id VARCHAR(10) PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    age INT NOT NULL,
    active BOOLEAN NOT NULL DEFAULT true
    );

-- seed some data
INSERT INTO users (id, name, age, active)
VALUES
    ('U1', 'Alice',   28, true),
    ('U2', 'Bob',     17, false),
    ('U3', 'Charlie', 32, true),
    ('U4', 'Diana',   22, true),
    ('U5', 'Eve',     15, false)
    ON CONFLICT (id) DO NOTHING;