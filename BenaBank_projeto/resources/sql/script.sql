-- phpMyAdmin SQL Dump
-- version 4.0.10deb1
-- http://www.phpmyadmin.net
--
-- Máquina: localhost
-- Data de Criação: 04-Dez-2015 às 21:20
-- Versão do servidor: 10.0.22-MariaDB-1~trusty-log
-- versão do PHP: 5.5.9-1ubuntu4.14

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Base de Dados: `projetojava`
--

-- --------------------------------------------------------

--
-- Estrutura da tabela `tb_administrador`
--

CREATE TABLE IF NOT EXISTS `tb_administrador` (
  `ID` int(10) unsigned NOT NULL,
  `LOGIN` varchar(30) NOT NULL,
  `SENHA` varchar(255) NOT NULL,
  PRIMARY KEY (`ID`),
  KEY `IDX_ADM_ID` (`ID`),
  KEY `IDX_ADM_LOGIN` (`LOGIN`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Extraindo dados da tabela `tb_administrador`
--

INSERT INTO `tb_administrador` (`ID`, `LOGIN`, `SENHA`) VALUES
(0, '111222333', '4D54497A4E4455324E7A673D');--SENHA É 12345678

-- --------------------------------------------------------

--
-- Estrutura da tabela `tb_cliente`
--

CREATE TABLE IF NOT EXISTS `tb_cliente` (
  `ID` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `CPF` varchar(11) NOT NULL,
  `NOME` varchar(100) NOT NULL,
  `AGENCIA` int(11) NOT NULL,
  `CONTA` int(11) NOT NULL,
  `SENHA` varchar(100) NOT NULL,
  `SALDO` double NOT NULL,
  `TIPO_CONTA` tinyint(1) DEFAULT '1',
  PRIMARY KEY (`ID`,`CPF`),
  UNIQUE KEY `CONTA` (`CONTA`),
  UNIQUE KEY `CPF` (`CPF`),
  KEY `IDX_CLIENTE_CPF` (`CPF`),
  KEY `IDX_CLIENTE_NOME` (`NOME`),
  KEY `IDX_CLIENTE_AGENCIA` (`AGENCIA`),
  KEY `IDX_CLIENTE_CONTA` (`CONTA`),
  KEY `IDX_CLIENTE_TIPO_CONTA` (`TIPO_CONTA`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=0 ;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
