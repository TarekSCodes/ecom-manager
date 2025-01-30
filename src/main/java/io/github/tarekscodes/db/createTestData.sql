-- Lieferanten einfügen
INSERT INTO supplier (supplierName, supplierNumber, supplierStatus) VALUES
('TechParts GmbH', 'SUP-001', 'Aktiv'),
('FastDelivery AG', 'SUP-002', 'Aktiv'),
('GreenEnergy Ltd.', 'SUP-003', 'Inaktiv'),
('Hardware Express', 'SUP-004', 'Aktiv'),
('AutoTech Solutions', 'SUP-005', 'Aktiv'),
('BuildIt Inc.', 'SUP-006', 'Inaktiv'),
('Logistik Pro', 'SUP-007', 'Aktiv'),
('SmartHome Experts', 'SUP-008', 'Aktiv'),
('MedTech Supplies', 'SUP-009', 'Aktiv'),
('OfficeWorld', 'SUP-010', 'Inaktiv');

-- Adressen einfügen
INSERT INTO address (street, streetNumber, postalCode, city, country) VALUES
('Hauptstraße', '12', '10115', 'Berlin', 'Deutschland'),
('Industrieweg', '34a', '70565', 'Stuttgart', 'Deutschland'),
('Marktplatz', '5', '80331', 'München', 'Deutschland'),
('Brunnenallee', '9', '50667', 'Köln', 'Deutschland'),
('Seestraße', '21', '14467', 'Potsdam', 'Deutschland'),
('Technologiepark', '2', '53111', 'Bonn', 'Deutschland'),
('Alpenstraße', '87', '5020', 'Salzburg', 'Österreich'),
('Innovationstraße', '15', '8001', 'Zürich', 'Schweiz'),
('Elbchaussee', '3', '20459', 'Hamburg', 'Deutschland'),
('Frankfurter Allee', '99', '10247', 'Berlin', 'Deutschland');

-- Verknüpfungen zwischen Lieferanten und Adressen
INSERT INTO supplier_address (supplierID, addressID, type) VALUES
(1, 1, 'Rechnung'),
(1, 2, 'Lieferung'),
(2, 3, 'Rechnung'),
(2, 4, 'Lieferung'),
(3, 5, 'Rechnung'),
(3, 6, 'Lieferung'),
(4, 7, 'Rechnung'),
(4, 8, 'Lieferung'),
(5, 9, 'Rechnung'),
(5, 10, 'Lieferung'),
(6, 1, 'Rechnung'),
(6, 3, 'Lieferung'),
(7, 2, 'Rechnung'),
(7, 4, 'Lieferung'),
(8, 5, 'Rechnung'),
(8, 6, 'Lieferung'),
(9, 7, 'Rechnung'),
(9, 8, 'Lieferung'),
(10, 9, 'Rechnung'),
(10, 10, 'Lieferung'),
(1, 3, 'Lieferung'),
(2, 5, 'Rechnung'),
(3, 7, 'Lieferung'),
(4, 9, 'Rechnung'),
(5, 2, 'Lieferung'),
(6, 4, 'Rechnung'),
(7, 6, 'Lieferung'),
(8, 8, 'Rechnung'),
(9, 10, 'Lieferung'),
(10, 1, 'Rechnung');
