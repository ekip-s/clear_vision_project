CREATE TABLE if not exists accounts (
    id UUID PRIMARY KEY,
    user_id UUID NOT NULL,
    type VARCHAR(20) NOT NULL CHECK (type IN ('CURRENT_ACCOUNT', 'CREDIT_CARD', 'DEPOSIT', 'INVESTMENT', 'CREDIT')),
    name VARCHAR(50) NOT NULL,
    status varchar(20) NOT NULL,
    balance NUMERIC(15,2),
    currency VARCHAR(10) NOT NULL,
    created_at TIMESTAMPTZ
);

CREATE INDEX IF NOT EXISTS idx_accounts_user_id ON accounts(user_id);

CREATE TABLE IF NOT EXISTS operation_category (
    id UUID PRIMARY KEY,
    code VARCHAR(50) UNIQUE NOT NULL,
    name VARCHAR(100) NOT NULL
);

CREATE TABLE IF NOT EXISTS operations (
    id UUID PRIMARY KEY,
    account_id UUID NOT NULL REFERENCES accounts(id) ON DELETE CASCADE,
    category_id UUID REFERENCES operation_category(id),
    status varchar(20) NOT NULL,
    amount NUMERIC(15,2) NOT NULL,
    description VARCHAR(100),
    created_at TIMESTAMPTZ
);

CREATE INDEX IF NOT EXISTS idx_operations_account_id ON operations(account_id);
CREATE INDEX IF NOT EXISTS idx_operations_account_created_at ON operations(account_id, created_at DESC);

CREATE TABLE IF NOT EXISTS recurring_operations (
    id UUID PRIMARY KEY,
    account_id UUID NOT NULL REFERENCES accounts(id) ON DELETE CASCADE,
    category_id UUID REFERENCES operation_category(id),
    repeat_cron VARCHAR(100),
    amount NUMERIC(15,2),
    next_run_date TIMESTAMPTZ NOT NULL,
    description VARCHAR(100),
    is_active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMPTZ,
    end_date TIMESTAMPTZ
);

CREATE INDEX IF NOT EXISTS idx_recurring_operations_account_id ON recurring_operations(account_id);
CREATE INDEX IF NOT EXISTS idx_recurring_operations_next_run ON recurring_operations(next_run_date);