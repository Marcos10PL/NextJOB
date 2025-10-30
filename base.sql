CREATE TABLE roles (
    id SERIAL PRIMARY KEY,
    name VARCHAR(50) NOT NULL UNIQUE  -- 'admin', 'user'
);

CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    full_name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role_id INT NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT now(),
    address VARCHAR(255),
    city VARCHAR(100),
    country VARCHAR(100),

    CONSTRAINT users_role_id_fkey FOREIGN KEY (role_id) REFERENCES roles(id)
);

CREATE TABLE industries (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL UNIQUE
);
-- IT, Education, Finance, Retail, Construction, Healthcare, Marketing

CREATE TABLE companies (
    id SERIAL PRIMARY KEY,
    owner_id INT,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    email VARCHAR(255) NOT NULL,
    website VARCHAR(255),
    industry_id INT NOT NULL,
    address VARCHAR(255),
    city VARCHAR(100),
    country VARCHAR(100),

    CONSTRAINT companies_owner_id_fkey FOREIGN KEY (owner_id) REFERENCES users(id) ON DELETE CASCADE,
    CONSTRAINT companies_industry_id_fkey FOREIGN KEY (industry_id) REFERENCES industries(id) ON DELETE SET NULL
);

CREATE TABLE contract_types (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL UNIQUE  
);
-- b2b, employment_contract, mandate_contract, specific_task_contract, internship_contract, other

CREATE TABLE job_announcements (
    id SERIAL PRIMARY KEY,
    author_id INT,   -- user who created the announcement
    company_id INT,  -- user who created the announcement but as a company
    title TEXT NOT NULL,
    description TEXT NOT NULL,
    contract_type_id INT NOT NULL,
    workload TEXT[] NOT NULL,
    work_mode TEXT[] NOT NULL, -- remote, on_site, hybrid
    salary_min INT NOT NULL,
    salary_max INT NOT NULL,
    is_cv_required BOOLEAN NOT NULL,
    payment_type VARCHAR(50) NOT NULL,  -- hourly, weekly, monthly
    created_at TIMESTAMP WITHOUT TIME ZONE DEFAULT now(),
    industry_id INT NOT NULL,
    address VARCHAR(255),
    city VARCHAR(100),
    country VARCHAR(100),

    CONSTRAINT job_announcements_author_id_fkey FOREIGN KEY (author_id) REFERENCES users(id),
    CONSTRAINT job_announcements_company_id_fkey FOREIGN KEY (company_id) REFERENCES companies(id),
    CONSTRAINT job_announcements_industry_id_fkey FOREIGN KEY (industry_id) REFERENCES industries(id),
    CONSTRAINT job_announcements_contract_type_id_fkey FOREIGN KEY (contract_type_id) REFERENCES contract_types(id)
);

CREATE TABLE job_seeker_profiles (
    id SERIAL PRIMARY KEY,
    user_id INT NOT NULL,
    full_name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    message TEXT NOT NULL,
    cv_name VARCHAR(255) DEFAULT '',
    cv_path VARCHAR(255),
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT now(),

    CONSTRAINT job_seeker_profiles_user_id_fkey FOREIGN KEY (user_id) REFERENCES users(id)
);