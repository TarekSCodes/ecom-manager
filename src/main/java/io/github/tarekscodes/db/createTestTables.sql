CREATE TABLE IF NOT EXISTS supplier (
	supplierID INTEGER PRIMARY KEY AUTOINCREMENT,
	supplierName TEXT NOT NULL,
	supplierNumber TEXT,
	supplierStatus TEXT DEFAULT 'Aktiv' CHECK (supplierStatus IN ('Aktiv', 'Inaktiv'))
);

CREATE TABLE IF NOT EXISTS address (
	addressID INTEGER PRIMARY KEY AUTOINCREMENT,
	street TEXT,
	streetNumber TEXT,
	postalCode TEXT,
	city TEXT,
	country TEXT
);

CREATE TABLE IF NOT EXISTS supplier_address (
	supplierID INTEGER,
	addressID INTEGER,
	type TEXT,
	PRIMARY KEY (supplierID, addressID),
	FOREIGN KEY (supplierID) REFERENCES supplier(supplierID) ON DELETE CASCADE,
	FOREIGN KEY (addressID) REFERENCES address(addressID) ON DELETE CASCADE
);




