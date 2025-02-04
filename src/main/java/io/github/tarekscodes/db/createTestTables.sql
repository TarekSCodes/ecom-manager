-- Tabellen ohne FKs
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

CREATE TABLE IF NOT EXISTS contactPerson (
	contactPersonID INTEGER PRIMARY KEY AUTOINCREMENT,
	firstName TEXT,
	lastName TEXT,
	email TEXT,
	phonePrefix TEXT,
	phoneNumber TEXT,
	faxNumber TEXT
);

-- Hilfstabellen
CREATE TABLE IF NOT EXISTS website (
	websiteID INTEGER PRIMARY KEY AUTOINCREMENT,
	supplierID INTEGER,
	websiteAddress TEXT,
	FOREIGN KEY (supplierID) REFERENCES supplier(supplierID)
);

CREATE TABLE IF NOT EXISTS supplier_address (
	supplierID INTEGER,
	addressID INTEGER,
	addressType TEXT,
	PRIMARY KEY (supplierID, addressID),
	FOREIGN KEY (supplierID) REFERENCES supplier(supplierID) ON DELETE CASCADE,
	FOREIGN KEY (addressID) REFERENCES address(addressID) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS supplier_contactPerson (
	supplierID INTEGER,
	contactPersonID INTEGER,
	contactPersonType TEXT,
	PRIMARY KEY (supplierID, contactPersonID),
	FOREIGN KEY (supplierID) REFERENCES supplier(supplierID),
	FOREIGN KEY (contactPersonID) REFERENCES contactPerson(contactPersonID)
);



