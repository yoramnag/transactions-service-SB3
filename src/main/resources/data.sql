CREATE TABLE IF NOT EXISTS `TRANSACTIONS` (
  `transaction_Id` int NOT NULL AUTO_INCREMENT,
  `credit_card` varchar(255) NOT NULL,
  `amount` FLOAT NOT NULL,
  `date` date NOT NULL,
  `mask_credit_card` varchar(255) NOT NULL,
  PRIMARY KEY (`transaction_Id`)
);