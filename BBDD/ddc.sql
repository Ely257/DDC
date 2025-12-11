-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 07-07-2025 a las 02:54:30
-- Versión del servidor: 10.4.32-MariaDB
-- Versión de PHP: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `ddc`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `cajas`
--

CREATE TABLE `cajas` (
  `ID` int(11) NOT NULL,
  `Nombre` varchar(100) NOT NULL,
  `Estado` varchar(10) NOT NULL DEFAULT 'Activo'
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

--
-- Volcado de datos para la tabla `cajas`
--

INSERT INTO `cajas` (`ID`, `Nombre`, `Estado`) VALUES
(2, 'Caja General', 'Activo');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `categorias`
--

CREATE TABLE `categorias` (
  `ID` int(11) NOT NULL,
  `Categoria` varchar(50) NOT NULL,
  `Estado` varchar(10) NOT NULL DEFAULT 'Activo'
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

--
-- Volcado de datos para la tabla `categorias`
--

INSERT INTO `categorias` (`ID`, `Categoria`, `Estado`) VALUES
(1, 'Batas', 'Activo'),
(2, 'Blusas', 'Activo'),
(3, 'Camisas', 'Activo'),
(4, 'Chaquetas', 'Activo'),
(5, 'Chemises', 'Activo'),
(6, 'Estampados', 'Activo'),
(7, 'Faldas', 'Activo'),
(8, 'Franelas', 'Activo'),
(9, 'Materia Prima', 'Activo'),
(10, 'Monos', 'Activo'),
(11, 'Pantalones', 'Activo'),
(12, 'Pullover', 'Activo'),
(13, 'Shorts', 'Activo'),
(14, 'Sombreros', 'Activo'),
(15, 'Sudaderas', 'Activo'),
(16, 'Uniformes', 'Activo');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `clientes`
--

CREATE TABLE `clientes` (
  `ID` int(11) NOT NULL,
  `Nombre` varchar(100) NOT NULL,
  `Telefono` varchar(15) NOT NULL,
  `Direccion` text NOT NULL,
  `Estado` varchar(10) NOT NULL DEFAULT 'Activo'
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

--
-- Volcado de datos para la tabla `clientes`
--

INSERT INTO `clientes` (`ID`, `Nombre`, `Telefono`, `Direccion`, `Estado`) VALUES
(2, 'Grados de Vzla C.A', '02617434486', 'Av Fuerzas armadas', 'Activo'),
(3, 'Albert Montiel ', '04146704978', 'Urb San Felipe sector 2 ', 'Activo'),
(4, 'Autorepuestos Medina Cars C.A', '', 'Av 15 sierra maestra calle 9', 'Activo'),
(5, 'Centro Clínico Medisur C.A', '', 'Calle 18 entre Av 7 y 8', 'Activo'),
(6, 'Centro de Belleza Monique C.A', '04146453457', 'Urb Coromoto Av 44', 'Activo'),
(7, 'Centro Medico Santa Barbara C.A', '04166616564', 'Calle 148 Barrio Sur América', 'Activo'),
(8, 'Policlínica Amado C.A', '02617905975', 'Calle 76 Av 34', 'Activo'),
(9, 'Editable.', '1', 'Editable.', 'Activo');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `compras`
--

CREATE TABLE `compras` (
  `ID` int(11) NOT NULL,
  `ID_proveedor` int(11) NOT NULL,
  `Total` decimal(10,2) NOT NULL,
  `Fecha` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

--
-- Volcado de datos para la tabla `compras`
--

INSERT INTO `compras` (`ID`, `ID_proveedor`, `Total`, `Fecha`) VALUES
(1, 5, 2500.00, '2024-07-29 18:14:01'),
(2, 1, 5000.00, '2024-07-29 22:58:07'),
(3, 1, 1500.00, '2024-08-06 21:12:40'),
(4, 1, 94450.00, '2024-08-06 21:15:11'),
(5, 1, 90000.00, '2024-08-06 21:16:40'),
(6, 1, 105000.00, '2024-08-06 21:18:49'),
(7, 4, 340.00, '2024-08-08 14:27:28'),
(8, 2, 25000.00, '2024-08-08 14:37:02'),
(9, 4, 5000.00, '2024-08-09 15:45:41'),
(10, 3, 2500.00, '2024-08-09 16:19:55'),
(11, 5, 1000.00, '2024-08-09 16:50:46'),
(12, 4, 60.00, '2024-08-14 05:51:21'),
(13, 5, 50.00, '2024-08-14 15:39:37'),
(14, 4, 4800.00, '2024-08-27 07:37:39'),
(15, 2, 5000.00, '2024-08-27 08:57:31'),
(16, 3, 5000.00, '2024-09-08 08:20:25'),
(17, 2, 5000.00, '2024-10-25 05:08:58'),
(18, 3, 5600.00, '2025-03-03 21:57:04'),
(19, 1, 4600.00, '2025-03-03 22:05:21'),
(20, 4, 4600.00, '2025-03-03 22:07:39'),
(21, 1, 5600.00, '2025-03-03 22:09:21'),
(22, 1, 78.00, '2025-03-03 22:09:37'),
(23, 3, 14500.00, '2025-03-03 22:11:32'),
(24, 4, 78.00, '2025-03-03 23:38:23'),
(25, 1, 5000.00, '2025-05-10 02:55:18'),
(26, 1, 5000.00, '2025-05-10 03:07:56'),
(27, 1, 2500.00, '2025-05-16 18:31:47');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `configuracion`
--

CREATE TABLE `configuracion` (
  `ID` int(11) NOT NULL,
  `Ruc` varchar(20) NOT NULL,
  `Nombre` varchar(150) NOT NULL,
  `Telefono` varchar(15) NOT NULL,
  `Direccion` varchar(150) NOT NULL,
  `Mensaje` varchar(150) NOT NULL,
  `IG` varchar(50) NOT NULL,
  `Correo` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

--
-- Volcado de datos para la tabla `configuracion`
--

INSERT INTO `configuracion` (`ID`, `Ruc`, `Nombre`, `Telefono`, `Direccion`, `Mensaje`, `IG`, `Correo`) VALUES
(1, 'J-31244349-9', 'Dynamic Data Control (C.A)', '0412-1742829', 'Sierra Maestra, Av.15 entre calle 16 y 17 San Francisco, Edo. Zulia, Venezuela.', 'Gracias por su preferencia.', 'grupo.ag', 'agconfeccionesca@gmail.com');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `detalle_cajas`
--

CREATE TABLE `detalle_cajas` (
  `ID` int(11) NOT NULL,
  `Fecha_apertura` datetime NOT NULL,
  `Fecha_cierre` datetime DEFAULT NULL,
  `Monto_inicial` decimal(10,2) NOT NULL,
  `Monto_final` decimal(10,2) DEFAULT NULL,
  `Total_ventas` int(11) DEFAULT NULL,
  `Estado` int(11) NOT NULL DEFAULT 1,
  `ID_usuario` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

--
-- Volcado de datos para la tabla `detalle_cajas`
--

INSERT INTO `detalle_cajas` (`ID`, `Fecha_apertura`, `Fecha_cierre`, `Monto_inicial`, `Monto_final`, `Total_ventas`, `Estado`, `ID_usuario`) VALUES
(1, '2024-07-29 18:53:34', '2024-07-18 19:27:25', 50.00, 3350.00, 1, 0, 1),
(2, '2024-07-29 18:55:52', '2024-07-18 19:27:25', 20.00, 1675.00, 1, 0, 2),
(3, '2024-07-29 18:59:03', '2024-07-18 19:27:25', 100.00, 10515.00, 3, 0, 2),
(4, '2024-08-09 12:51:53', '2024-07-18 19:27:25', 50.00, 13350.00, 2, 0, 1),
(5, '2024-07-30 07:48:17', '2024-07-18 19:27:25', 10.00, 23350.00, 3, 0, 1),
(6, '2024-07-30 07:49:18', '2024-07-18 19:27:25', 50.00, 23350.00, 3, 0, 1),
(7, '2024-08-12 00:59:28', '2024-07-18 19:27:25', 100.00, 23350.00, 3, 0, 1),
(8, '2024-08-12 03:13:57', '2024-07-18 19:27:25', 100.00, 23350.00, 3, 0, 1),
(9, '2024-08-12 03:14:19', '2024-07-18 19:27:25', 100.00, 23350.00, 3, 0, 1),
(10, '2024-08-13 00:43:46', '2024-07-18 19:27:25', 150.00, 23350.00, 3, 0, 1),
(11, '2024-08-13 00:48:03', '2024-07-18 19:27:25', 200.00, 27850.00, 4, 0, 1),
(12, '2024-08-13 13:47:50', NULL, 250.00, 27850.00, 4, 0, 1),
(13, '2024-08-13 13:48:21', NULL, 300.00, 27850.00, 4, 0, 1),
(14, '2024-08-13 14:07:22', '2024-07-18 19:27:25', 400.00, 27850.00, 4, 0, 1),
(15, '2024-08-13 14:08:15', '2024-07-18 19:27:25', 500.00, 27850.00, 4, 0, 1),
(16, '2024-08-13 14:08:29', '2024-07-18 19:27:25', 600.00, 27850.00, 4, 0, 1),
(17, '2024-08-13 14:10:58', '2024-07-18 19:27:25', 450.00, 27850.00, 4, 0, 1),
(18, '2024-08-13 14:12:46', '2024-07-18 19:27:25', 425.00, 27850.00, 4, 0, 1),
(19, '2024-08-13 14:15:31', '2024-07-18 19:27:25', 100.00, 27850.00, 4, 0, 1),
(20, '2024-08-13 14:15:55', '2024-07-18 19:27:25', 500.00, 27850.00, 4, 0, 1),
(21, '2024-08-13 14:33:39', '2024-07-18 19:27:25', 400.00, 27850.00, 4, 0, 1),
(22, '2024-08-13 14:35:51', '2024-07-18 19:27:25', 450.00, 27850.00, 4, 0, 1),
(23, '2024-08-13 14:58:00', '2024-07-18 19:27:25', 350.00, 27850.00, 4, 0, 1),
(24, '2024-08-13 20:59:02', '2024-08-13 20:59:02', 450.00, 28855.00, 5, 0, 1),
(25, '2024-08-13 21:16:21', '2024-08-13 21:18:33', 250.00, 36355.00, 7, 0, 1),
(26, '2024-08-13 23:55:07', '2024-08-13 23:55:56', 450.00, 41355.00, 8, 0, 1),
(27, '2024-08-14 01:48:23', '2024-08-14 01:51:37', 600.00, 46355.00, 9, 0, 1),
(28, '2024-08-14 08:31:42', '2024-08-14 08:33:13', 50.00, 13515.00, 4, 0, 2),
(29, '2024-08-14 11:18:37', '2024-08-27 01:15:07', 20.00, 93355.00, 15, 0, 1),
(30, '2024-08-27 02:38:28', '2024-08-27 05:02:13', 50.00, 131355.00, 22, 0, 1),
(31, '2024-09-08 04:19:51', '2024-10-25 00:59:34', 34.00, 141355.00, 24, 0, 1),
(32, '2024-10-25 00:59:40', '2024-10-25 01:09:18', 50.00, 141460.00, 25, 0, 1),
(33, '2024-10-25 01:09:21', '2025-03-03 18:00:06', 45.00, 141460.00, 25, 0, 1),
(34, '2025-03-03 18:00:11', '2025-03-03 19:38:51', 49.00, 152260.00, 26, 0, 1),
(35, '2025-03-03 19:38:55', '2025-05-09 23:23:43', 56.00, 182260.00, 30, 0, 1),
(36, '2025-05-09 23:23:48', '2025-05-09 23:24:09', 45.00, 185060.00, 31, 0, 1),
(37, '2025-05-09 23:24:24', NULL, 13.00, NULL, NULL, 1, 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `detalle_compra`
--

CREATE TABLE `detalle_compra` (
  `ID` int(11) NOT NULL,
  `ID_compra` int(11) NOT NULL,
  `ID_producto` int(11) NOT NULL,
  `Precio` decimal(10,2) NOT NULL,
  `Cantidad` int(11) NOT NULL,
  `Subtotal` decimal(10,2) NOT NULL,
  `Fecha` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

--
-- Volcado de datos para la tabla `detalle_compra`
--

INSERT INTO `detalle_compra` (`ID`, `ID_compra`, `ID_producto`, `Precio`, `Cantidad`, `Subtotal`, `Fecha`) VALUES
(1, 1, 5, 50.00, 50, 2500.00, '2024-07-29 18:14:02'),
(2, 2, 5, 50.00, 100, 5000.00, '2024-07-29 22:58:07'),
(3, 3, 20, 100.00, 15, 1500.00, '2024-08-06 21:12:40'),
(4, 4, 1, 50.00, 50, 2500.00, '2024-08-06 21:15:11'),
(5, 4, 2, 34.00, 50, 1700.00, '2024-08-06 21:15:11'),
(6, 4, 3, 455.00, 50, 22750.00, '2024-08-06 21:15:11'),
(7, 4, 4, 500.00, 50, 25000.00, '2024-08-06 21:15:12'),
(8, 4, 5, 50.00, 50, 2500.00, '2024-08-06 21:15:12'),
(9, 4, 6, 400.00, 50, 20000.00, '2024-08-06 21:15:12'),
(10, 4, 7, 100.00, 50, 5000.00, '2024-08-06 21:15:12'),
(11, 4, 8, 100.00, 50, 5000.00, '2024-08-06 21:15:12'),
(12, 4, 9, 100.00, 50, 5000.00, '2024-08-06 21:15:12'),
(13, 4, 10, 100.00, 50, 5000.00, '2024-08-06 21:15:12'),
(14, 5, 11, 100.00, 50, 5000.00, '2024-08-06 21:16:41'),
(15, 5, 12, 100.00, 50, 5000.00, '2024-08-06 21:16:41'),
(16, 5, 13, 100.00, 50, 5000.00, '2024-08-06 21:16:41'),
(17, 5, 14, 100.00, 50, 5000.00, '2024-08-06 21:16:41'),
(18, 5, 15, 100.00, 50, 5000.00, '2024-08-06 21:16:41'),
(19, 5, 16, 100.00, 50, 5000.00, '2024-08-06 21:16:41'),
(20, 5, 17, 100.00, 50, 5000.00, '2024-08-06 21:16:41'),
(21, 5, 18, 100.00, 50, 5000.00, '2024-08-06 21:16:42'),
(22, 5, 19, 100.00, 50, 5000.00, '2024-08-06 21:16:42'),
(23, 5, 20, 100.00, 50, 5000.00, '2024-08-06 21:16:42'),
(24, 5, 21, 100.00, 50, 5000.00, '2024-08-06 21:16:42'),
(25, 5, 22, 100.00, 50, 5000.00, '2024-08-06 21:16:43'),
(26, 5, 23, 100.00, 50, 5000.00, '2024-08-06 21:16:43'),
(27, 5, 24, 100.00, 50, 5000.00, '2024-08-06 21:16:43'),
(28, 5, 25, 100.00, 50, 5000.00, '2024-08-06 21:16:43'),
(29, 5, 26, 100.00, 50, 5000.00, '2024-08-06 21:16:43'),
(30, 5, 27, 100.00, 50, 5000.00, '2024-08-06 21:16:43'),
(31, 5, 28, 100.00, 50, 5000.00, '2024-08-06 21:16:43'),
(32, 6, 30, 100.00, 50, 5000.00, '2024-08-06 21:18:49'),
(33, 6, 31, 100.00, 50, 5000.00, '2024-08-06 21:18:49'),
(34, 6, 32, 100.00, 50, 5000.00, '2024-08-06 21:18:49'),
(35, 6, 33, 100.00, 50, 5000.00, '2024-08-06 21:18:49'),
(36, 6, 34, 100.00, 50, 5000.00, '2024-08-06 21:18:50'),
(37, 6, 35, 100.00, 50, 5000.00, '2024-08-06 21:18:50'),
(38, 6, 36, 100.00, 50, 5000.00, '2024-08-06 21:18:50'),
(39, 6, 37, 100.00, 50, 5000.00, '2024-08-06 21:18:50'),
(40, 6, 38, 100.00, 50, 5000.00, '2024-08-06 21:18:50'),
(41, 6, 39, 100.00, 50, 5000.00, '2024-08-06 21:18:50'),
(42, 6, 40, 100.00, 50, 5000.00, '2024-08-06 21:18:50'),
(43, 6, 41, 100.00, 50, 5000.00, '2024-08-06 21:18:50'),
(44, 6, 42, 100.00, 50, 5000.00, '2024-08-06 21:18:51'),
(45, 6, 43, 100.00, 50, 5000.00, '2024-08-06 21:18:51'),
(46, 6, 44, 100.00, 50, 5000.00, '2024-08-06 21:18:51'),
(47, 6, 45, 100.00, 50, 5000.00, '2024-08-06 21:18:51'),
(48, 6, 46, 100.00, 50, 5000.00, '2024-08-06 21:18:51'),
(49, 6, 47, 100.00, 50, 5000.00, '2024-08-06 21:18:51'),
(50, 6, 48, 100.00, 50, 5000.00, '2024-08-06 21:18:51'),
(51, 6, 49, 100.00, 50, 5000.00, '2024-08-06 21:18:51'),
(52, 6, 50, 100.00, 50, 5000.00, '2024-08-06 21:18:52'),
(53, 7, 2, 34.00, 10, 340.00, '2024-08-08 14:27:28'),
(54, 8, 4, 500.00, 50, 25000.00, '2024-08-08 14:37:02'),
(55, 9, 50, 100.00, 50, 5000.00, '2024-08-09 15:45:41'),
(56, 10, 30, 100.00, 10, 1000.00, '2024-08-09 16:19:55'),
(57, 10, 31, 100.00, 15, 1500.00, '2024-08-09 16:19:56'),
(58, 11, 10, 100.00, 5, 500.00, '2024-08-09 16:50:46'),
(59, 11, 15, 100.00, 5, 500.00, '2024-08-09 16:50:47'),
(60, 12, 56, 1.00, 60, 60.00, '2024-08-14 05:51:21'),
(61, 13, 56, 1.00, 50, 50.00, '2024-08-14 15:39:38'),
(62, 14, 10, 100.00, 25, 2500.00, '2024-08-27 07:37:39'),
(63, 14, 46, 100.00, 23, 2300.00, '2024-08-27 07:37:39'),
(64, 15, 45, 100.00, 50, 5000.00, '2024-08-27 08:57:32'),
(65, 16, 34, 100.00, 50, 5000.00, '2024-09-08 08:20:25'),
(66, 17, 34, 100.00, 50, 5000.00, '2024-10-25 05:08:58'),
(67, 18, 50, 100.00, 56, 5600.00, '2025-03-03 21:57:04'),
(68, 19, 50, 100.00, 46, 4600.00, '2025-03-03 22:05:21'),
(69, 20, 50, 100.00, 46, 4600.00, '2025-03-03 22:07:39'),
(70, 21, 50, 100.00, 56, 5600.00, '2025-03-03 22:09:21'),
(71, 22, 56, 1.00, 78, 78.00, '2025-03-03 22:09:37'),
(72, 23, 45, 100.00, 145, 14500.00, '2025-03-03 22:11:32'),
(73, 24, 54, 1.00, 78, 78.00, '2025-03-03 23:38:23'),
(74, 25, 14, 100.00, 50, 5000.00, '2025-05-10 02:55:18'),
(75, 26, 14, 100.00, 50, 5000.00, '2025-05-10 03:07:56'),
(76, 27, 50, 100.00, 25, 2500.00, '2025-05-16 18:31:47');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `detalle_ventas`
--

CREATE TABLE `detalle_ventas` (
  `ID` int(11) NOT NULL,
  `ID_venta` int(11) NOT NULL,
  `ID_producto` int(11) NOT NULL,
  `Precio` decimal(10,2) NOT NULL,
  `Cantidad` int(11) NOT NULL,
  `Subtotal` decimal(10,2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

--
-- Volcado de datos para la tabla `detalle_ventas`
--

INSERT INTO `detalle_ventas` (`ID`, `ID_venta`, `ID_producto`, `Precio`, `Cantidad`, `Subtotal`) VALUES
(1, 1, 2, 67.00, 50, 3350.00),
(2, 2, 2, 67.00, 25, 1675.00),
(3, 3, 2, 67.00, 20, 1340.00),
(4, 3, 5, 100.00, 50, 5000.00),
(5, 4, 5, 100.00, 25, 2500.00),
(6, 5, 50, 200.00, 50, 10000.00),
(7, 6, 10, 200.00, 15, 3000.00),
(8, 6, 20, 200.00, 35, 7000.00),
(9, 7, 10, 200.00, 10, 2000.00),
(10, 7, 5, 100.00, 25, 2500.00),
(11, 8, 2, 67.00, 15, 1005.00),
(12, 9, 1, 100.00, 25, 2500.00),
(13, 10, 49, 200.00, 25, 5000.00),
(14, 11, 45, 200.00, 25, 5000.00),
(15, 12, 12, 200.00, 25, 5000.00),
(16, 13, 10, 200.00, 15, 3000.00),
(17, 14, 5, 100.00, 25, 2500.00),
(18, 15, 1, 100.00, 15, 1500.00),
(19, 16, 3, 700.00, 25, 17500.00),
(20, 17, 6, 700.00, 25, 17500.00),
(21, 18, 45, 200.00, 15, 3000.00),
(22, 19, 46, 200.00, 25, 5000.00),
(23, 20, 35, 200.00, 25, 5000.00),
(24, 21, 40, 200.00, 25, 5000.00),
(25, 22, 45, 200.00, 5, 1000.00),
(26, 23, 25, 200.00, 25, 5000.00),
(27, 23, 10, 200.00, 10, 2000.00),
(28, 23, 23, 200.00, 25, 5000.00),
(29, 24, 35, 200.00, 10, 2000.00),
(30, 24, 46, 200.00, 15, 3000.00),
(31, 25, 12, 200.00, 15, 3000.00),
(32, 25, 11, 200.00, 25, 5000.00),
(33, 26, 12, 200.00, 10, 2000.00),
(34, 27, 24, 200.00, 25, 5000.00),
(35, 28, 34, 200.00, 25, 5000.00),
(36, 29, 56, 3.00, 35, 105.00),
(37, 30, 50, 200.00, 54, 10800.00),
(38, 31, 50, 200.00, 100, 20000.00),
(39, 32, 14, 200.00, 15, 3000.00),
(40, 33, 15, 200.00, 25, 5000.00),
(41, 34, 25, 200.00, 10, 2000.00),
(42, 35, 45, 200.00, 14, 2800.00),
(43, 36, 1, 100.00, 4, 400.00),
(44, 36, 4, 1000.00, 2, 2000.00),
(45, 37, 1, 100.00, 5, 500.00),
(46, 37, 3, 700.00, 1, 700.00),
(47, 38, 50, 200.00, 50, 10000.00);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `medidas`
--

CREATE TABLE `medidas` (
  `ID` int(11) NOT NULL,
  `Medida` varchar(20) NOT NULL,
  `Nombre_corto` varchar(8) NOT NULL,
  `Estado` varchar(10) NOT NULL DEFAULT 'Activo'
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

--
-- Volcado de datos para la tabla `medidas`
--

INSERT INTO `medidas` (`ID`, `Medida`, `Nombre_corto`, `Estado`) VALUES
(1, 'METROS', 'MTS.', 'Activo'),
(2, 'DETAL', 'DTL.', 'Activo');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `productos`
--

CREATE TABLE `productos` (
  `ID` int(11) NOT NULL,
  `Codigo` varchar(20) NOT NULL,
  `Descripcion` varchar(255) NOT NULL,
  `Cantidad` int(11) NOT NULL DEFAULT 0,
  `Precio_compra` decimal(10,2) NOT NULL,
  `Precio_venta` decimal(10,2) NOT NULL,
  `ID_proveedor` int(11) NOT NULL,
  `ID_medida` int(11) NOT NULL,
  `ID_categoria` int(11) NOT NULL,
  `Estado` varchar(10) NOT NULL DEFAULT 'Activo'
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

--
-- Volcado de datos para la tabla `productos`
--

INSERT INTO `productos` (`ID`, `Codigo`, `Descripcion`, `Cantidad`, `Precio_compra`, `Precio_venta`, `ID_proveedor`, `ID_medida`, `ID_categoria`, `Estado`) VALUES
(1, '1', 'Camisas de Caballero', 1, 50.00, 100.00, 3, 2, 3, 'Activo'),
(2, '2', 'Blusas Quirúrgicas', 50, 34.00, 67.00, 3, 2, 2, 'Activo'),
(3, '3', 'Camisas de Dama', 24, 455.00, 700.00, 5, 2, 3, 'Activo'),
(4, '4', 'Franelas de Dama y Caballero', 98, 500.00, 1000.00, 2, 2, 8, 'Activo'),
(5, '5', 'Chemis de Dama y Caballero', 25, 50.00, 100.00, 5, 2, 5, 'Activo'),
(6, '6', 'Conjunto de Mono', 25, 400.00, 700.00, 5, 2, 10, 'Activo'),
(7, '7', 'Pantalones', 50, 100.00, 200.00, 3, 2, 11, 'Activo'),
(8, '8', 'Batas Largas', 50, 100.00, 200.00, 4, 2, 1, 'Activo'),
(9, '9', 'Batas de Cardiólogo', 50, 100.00, 200.00, 5, 2, 1, 'Activo'),
(10, '10', 'Estampados', 30, 100.00, 200.00, 2, 2, 6, 'Activo'),
(11, '11', 'Chemis Blanca Deporte', 25, 100.00, 200.00, 3, 2, 5, 'Activo'),
(12, '12', 'Chemis Tradicional Blanca', 0, 100.00, 200.00, 5, 2, 5, 'Activo'),
(13, '13', 'Short Niño Azul Oscuro Gabardina', 50, 100.00, 200.00, 4, 2, 13, 'Activo'),
(14, '14', 'Falda Niña Azul Oscuro Gabardina', 135, 100.00, 200.00, 2, 2, 7, 'Activo'),
(15, '15', 'Mono Niño Taslan Celeste', 30, 100.00, 200.00, 4, 2, 10, 'Activo'),
(16, '16', 'Falda Niña Celeste', 50, 100.00, 200.00, 4, 2, 7, 'Activo'),
(17, '17', 'Jumper Azul Oscuro Gabardina', 50, 100.00, 200.00, 2, 2, 16, 'Activo'),
(18, '18', 'Chemis Tradicional Amarilla', 50, 100.00, 200.00, 3, 2, 5, 'Activo'),
(19, '19', 'Mono Grandes Ligas Azul Oscuro', 50, 100.00, 200.00, 5, 2, 10, 'Activo'),
(20, '20', 'Chemis Deporte Gris Melange', 30, 100.00, 200.00, 4, 2, 5, 'Activo'),
(21, '21', 'Mono Grandes Ligas Negro', 50, 100.00, 200.00, 3, 2, 10, 'Activo'),
(22, '22', 'Pullover Azul Oscuro Grandes Ligas', 50, 100.00, 200.00, 2, 2, 12, 'Activo'),
(23, '23', 'Falda Short Bies Rojo', 25, 100.00, 200.00, 5, 2, 7, 'Activo'),
(24, '24', 'Camisa Dacron', 25, 100.00, 200.00, 3, 2, 3, 'Activo'),
(25, '25', 'Mono Deportivo Taslan Azul', 15, 100.00, 200.00, 3, 2, 10, 'Activo'),
(26, '26', 'Gorras', 50, 100.00, 200.00, 1, 2, 14, 'Activo'),
(27, '27', 'Chaquetas Escolares', 50, 100.00, 200.00, 2, 2, 4, 'Activo'),
(28, '28', 'Franela Blanca Algodón Unisex', 50, 100.00, 200.00, 3, 2, 8, 'Activo'),
(29, '29', 'Short Licra', 0, 100.00, 200.00, 5, 2, 13, 'Activo'),
(30, '30', 'Bata de Laboratorio', 60, 100.00, 200.00, 4, 2, 1, 'Activo'),
(31, '31', 'Faldas Escolares', 65, 100.00, 200.00, 2, 2, 7, 'Activo'),
(32, '32', 'Jumper', 50, 100.00, 200.00, 4, 2, 16, 'Activo'),
(33, '33', 'Taslan', 50, 100.00, 200.00, 2, 1, 9, 'Activo'),
(34, '34', 'Dacron', 125, 100.00, 200.00, 3, 1, 9, 'Activo'),
(35, '35', 'Melange', 15, 100.00, 200.00, 3, 1, 9, 'Activo'),
(36, '36', 'Grandes Ligas', 50, 100.00, 200.00, 1, 1, 9, 'Activo'),
(37, '37', 'Scuba', 50, 100.00, 200.00, 5, 1, 9, 'Activo'),
(38, '38', 'Gabardina Strech', 50, 100.00, 200.00, 4, 1, 9, 'Activo'),
(39, '39', 'Chemis Piquet', 50, 100.00, 200.00, 1, 1, 9, 'Activo'),
(40, '40', 'Piquet Armani', 25, 100.00, 200.00, 3, 1, 9, 'Activo'),
(41, '41', 'Micromet', 50, 100.00, 200.00, 1, 1, 9, 'Activo'),
(42, '42', 'Micro Drill', 50, 100.00, 200.00, 4, 1, 9, 'Activo'),
(43, '43', 'Drill Toscana', 50, 100.00, 200.00, 2, 1, 9, 'Activo'),
(44, '44', 'Romana', 50, 100.00, 200.00, 5, 1, 9, 'Activo'),
(45, '45', 'Suéter Escolar', 186, 100.00, 200.00, 1, 2, 9, 'Activo'),
(46, '46', 'Doble Vía', 33, 100.00, 200.00, 3, 1, 9, 'Activo'),
(47, '47', 'Oxfort', 50, 100.00, 200.00, 1, 1, 9, 'Activo'),
(48, '48', 'Unioffice Blanco', 50, 100.00, 200.00, 5, 1, 9, 'Activo'),
(49, '49', 'Atlética', 25, 100.00, 200.00, 1, 1, 9, 'Activo'),
(50, '50', 'Hilos de Filetear ', 75, 100.00, 200.00, 4, 2, 9, 'Activo'),
(51, '51', 'Hilos de Coser', 0, 1.00, 3.00, 2, 2, 9, 'Activo'),
(52, '52', 'Hilos de Coser (Sable)', 0, 1.00, 3.00, 3, 2, 9, 'Activo'),
(53, '53', 'Hilos de Coser (Bambary)', 0, 1.00, 3.00, 4, 2, 9, 'Activo'),
(54, '54', 'Hilos de Coser (Overtex)', 78, 1.00, 3.00, 5, 2, 9, 'Activo'),
(55, '55', 'Hilos de Coser (Dai)', 0, 1.00, 3.00, 4, 2, 9, 'Activo'),
(56, '56', 'Hilos de Coser (Variable)', 153, 1.00, 3.00, 1, 2, 9, 'Activo'),
(57, '57', 'Hilos de Coser (Flamingo)', 0, 1.00, 3.00, 3, 2, 9, 'Activo'),
(58, '58', 'Hilos de Coser (Superfibra)', 0, 1.00, 3.00, 5, 2, 9, 'Activo'),
(59, '59', 'Hilos de Bordar (Durafil)', 0, 2.00, 3.00, 1, 2, 9, 'Activo'),
(60, '60', 'Hilos de Bordar (Dunhuang)', 0, 2.00, 3.00, 2, 2, 9, 'Activo'),
(61, '61', 'Hilos de Bordar(Silko)', 0, 2.00, 3.00, 3, 2, 9, 'Activo'),
(62, '62', 'Hilos de Bordar (Samurai)', 0, 2.00, 3.00, 3, 2, 9, 'Activo'),
(63, '63', 'Hilos de Bordar (Royal)', 0, 2.00, 3.00, 3, 2, 9, 'Activo'),
(64, '64', 'Hilos de Bordar (Prime)', 0, 2.00, 3.00, 4, 2, 9, 'Activo'),
(65, '65', 'Hilos de Bordar (Yamabeth)', 0, 2.00, 3.00, 4, 2, 9, 'Activo'),
(66, '66', 'Hilos de Bordar (Lumi)', 0, 2.00, 3.00, 5, 2, 9, 'Activo'),
(67, '67', 'Elásticas Tapaboca', 0, 1.00, 3.00, 1, 1, 9, 'Activo'),
(68, '68', 'Elásticas Rollo (Finita)', 0, 8.00, 10.00, 5, 2, 9, 'Activo'),
(69, '69', 'Elásticas Rollo', 0, 11.00, 12.00, 1, 2, 9, 'Activo'),
(70, '70', 'Elásticas Pijamas Rollo', 0, 11.00, 12.00, 2, 2, 9, 'Activo'),
(71, '71', 'Elásticas Cordón ', 0, 1.00, 3.00, 3, 2, 9, 'Activo'),
(72, '72', 'Pelon ', 0, 1.00, 3.00, 4, 2, 9, 'Activo'),
(73, '73', 'Pelon Bordados ', 0, 1.00, 3.00, 5, 2, 9, 'Activo'),
(74, '74', 'Botones ', 0, 1.00, 3.00, 1, 2, 9, 'Activo'),
(75, '75', 'Botones Transparentes', 0, 1.00, 3.00, 2, 2, 9, 'Activo'),
(76, '76', 'Tazas', 0, 1.00, 3.00, 5, 2, 9, 'Activo'),
(77, '77', 'Cierre Mágico ', 0, 1.00, 3.00, 1, 2, 9, 'Activo'),
(78, '78', 'Cierre Metal ', 0, 1.00, 3.00, 3, 2, 9, 'Activo'),
(79, '79', 'Cierre Invisible ', 0, 1.00, 3.00, 3, 2, 9, 'Activo'),
(80, '80', 'Cierre Plastico', 0, 1.00, 3.00, 5, 2, 9, 'Activo');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `proveedor`
--

CREATE TABLE `proveedor` (
  `ID` int(11) NOT NULL,
  `Ruc` varchar(20) NOT NULL,
  `Proveedor` varchar(200) NOT NULL,
  `Telefono` varchar(15) NOT NULL,
  `Direccion` text NOT NULL,
  `Estado` varchar(10) NOT NULL DEFAULT 'Activo'
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

--
-- Volcado de datos para la tabla `proveedor`
--

INSERT INTO `proveedor` (`ID`, `Ruc`, `Proveedor`, `Telefono`, `Direccion`, `Estado`) VALUES
(1, 'J-40750958', 'Convitextiles S.A', '04146660602', 'Calle 100 Sabaneta Local #40-105', 'Activo'),
(2, 'J-298147782', 'Servicios Dublin C.A', '04143409358', 'Calle 149 (Uslar) N. Civico 101-185, Parcelas 13 y 14 (Valencia)', 'Activo'),
(3, 'J-40228309-7', 'Distitex C.A', '02617231817', 'Av. Bolivar Norte C.C Profesional El Camoruco piso 24 ofic. 24-4-4 (Valencia)', 'Activo'),
(4, 'J-29615916-5', 'Textizulca ', '04146822625', 'Sector Av. 16, C.C San Felipe II, casco central (Maracaibo)', 'Activo'),
(5, 'J-31285456', 'Creaciones Johan ', '04246744947', 'Casco central calle 98 Bolivar (Maracaibo)', 'Activo');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuarios`
--

CREATE TABLE `usuarios` (
  `ID` int(11) NOT NULL,
  `Usuario` varchar(10) NOT NULL,
  `Nombre` varchar(100) NOT NULL,
  `Clave` varchar(50) NOT NULL,
  `ID_caja` int(11) NOT NULL,
  `Rol` varchar(20) NOT NULL,
  `Estado` varchar(10) NOT NULL DEFAULT 'Activo'
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

--
-- Volcado de datos para la tabla `usuarios`
--

INSERT INTO `usuarios` (`ID`, `Usuario`, `Nombre`, `Clave`, `ID_caja`, `Rol`, `Estado`) VALUES
(1, 'corzito', 'José David Corzo', '777', 2, 'Administrador', 'Activo'),
(2, 'Alis', 'Alis Guerrero', 'alis123', 2, 'Presidente(a)', 'Activo'),
(3, 'Katy', 'Katiuska', 'katy1234', 2, 'Empleado(a)', 'Activo'),
(4, 'Ziomara', 'Ziomara', 'zio1234', 2, 'Empleado(a)', 'Activo'),
(5, 'Andrea', 'Andrea', 'andre123', 2, 'Empleado(a)', 'Inactivo'),
(6, 'Editable', 'Editable', '12345', 2, 'Empleado(a)', 'Activo');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `ventas`
--

CREATE TABLE `ventas` (
  `ID` int(11) NOT NULL,
  `ID_cliente` int(11) NOT NULL,
  `Total` decimal(10,2) NOT NULL,
  `Fecha` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `ID_user` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

--
-- Volcado de datos para la tabla `ventas`
--

INSERT INTO `ventas` (`ID`, `ID_cliente`, `Total`, `Fecha`, `ID_user`) VALUES
(1, 5, 3350.00, '2024-07-29 22:50:41', 1),
(2, 2, 1675.00, '2024-07-29 22:55:32', 2),
(3, 2, 6340.00, '2024-07-29 22:58:30', 2),
(4, 2, 2500.00, '2024-07-29 22:58:54', 2),
(5, 7, 10000.00, '2024-08-09 15:47:09', 1),
(6, 8, 10000.00, '2024-08-09 17:02:56', 1),
(7, 2, 4500.00, '2024-08-13 04:47:25', 1),
(8, 3, 1005.00, '2024-08-14 00:53:41', 1),
(9, 2, 2500.00, '2024-08-14 01:16:39', 1),
(10, 7, 5000.00, '2024-08-14 01:18:13', 1),
(11, 4, 5000.00, '2024-08-14 03:55:29', 1),
(12, 2, 5000.00, '2024-08-14 05:49:22', 1),
(13, 8, 3000.00, '2024-08-14 12:32:22', 2),
(14, 3, 2500.00, '2024-08-14 15:18:56', 1),
(15, 3, 1500.00, '2024-08-14 15:19:47', 1),
(16, 4, 17500.00, '2024-08-14 15:23:29', 1),
(17, 7, 17500.00, '2024-08-14 15:25:33', 1),
(18, 7, 3000.00, '2024-08-14 15:31:12', 1),
(19, 2, 5000.00, '2024-08-14 15:34:36', 1),
(20, 8, 5000.00, '2024-08-27 06:38:30', 1),
(21, 2, 5000.00, '2024-08-27 06:47:28', 1),
(22, 2, 1000.00, '2024-08-27 06:47:53', 1),
(23, 4, 12000.00, '2024-08-27 06:50:53', 1),
(24, 4, 5000.00, '2024-08-27 07:12:43', 1),
(25, 8, 8000.00, '2024-08-27 07:24:11', 1),
(26, 5, 2000.00, '2024-08-27 08:54:59', 1),
(27, 4, 5000.00, '2024-09-08 08:19:56', 1),
(28, 8, 5000.00, '2024-10-25 04:59:15', 1),
(29, 4, 105.00, '2024-10-25 05:08:38', 1),
(30, 7, 10800.00, '2025-03-03 22:02:19', 1),
(31, 6, 20000.00, '2025-03-03 23:39:01', 1),
(32, 2, 3000.00, '2025-05-10 02:48:08', 1),
(33, 2, 5000.00, '2025-05-10 02:49:15', 1),
(34, 2, 2000.00, '2025-05-10 02:49:43', 1),
(35, 2, 2800.00, '2025-05-10 03:23:59', 1),
(36, 3, 2400.00, '2025-05-10 12:48:35', 1),
(37, 8, 1200.00, '2025-05-10 12:54:29', 1),
(38, 2, 10000.00, '2025-05-16 18:37:22', 1);

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `cajas`
--
ALTER TABLE `cajas`
  ADD PRIMARY KEY (`ID`);

--
-- Indices de la tabla `categorias`
--
ALTER TABLE `categorias`
  ADD PRIMARY KEY (`ID`);

--
-- Indices de la tabla `clientes`
--
ALTER TABLE `clientes`
  ADD PRIMARY KEY (`ID`);

--
-- Indices de la tabla `compras`
--
ALTER TABLE `compras`
  ADD PRIMARY KEY (`ID`),
  ADD KEY `ID_proveedor` (`ID_proveedor`);

--
-- Indices de la tabla `configuracion`
--
ALTER TABLE `configuracion`
  ADD PRIMARY KEY (`ID`);

--
-- Indices de la tabla `detalle_cajas`
--
ALTER TABLE `detalle_cajas`
  ADD PRIMARY KEY (`ID`),
  ADD KEY `ID_usuario` (`ID_usuario`);

--
-- Indices de la tabla `detalle_compra`
--
ALTER TABLE `detalle_compra`
  ADD PRIMARY KEY (`ID`),
  ADD KEY `ID_compra` (`ID_compra`),
  ADD KEY `ID_producto` (`ID_producto`);

--
-- Indices de la tabla `detalle_ventas`
--
ALTER TABLE `detalle_ventas`
  ADD PRIMARY KEY (`ID`),
  ADD KEY `ID_producto` (`ID_producto`),
  ADD KEY `ID_venta` (`ID_venta`);

--
-- Indices de la tabla `medidas`
--
ALTER TABLE `medidas`
  ADD PRIMARY KEY (`ID`);

--
-- Indices de la tabla `productos`
--
ALTER TABLE `productos`
  ADD PRIMARY KEY (`ID`),
  ADD KEY `ID_proveedor` (`ID_proveedor`),
  ADD KEY `ID_medida` (`ID_medida`),
  ADD KEY `ID_categoria` (`ID_categoria`);

--
-- Indices de la tabla `proveedor`
--
ALTER TABLE `proveedor`
  ADD PRIMARY KEY (`ID`);

--
-- Indices de la tabla `usuarios`
--
ALTER TABLE `usuarios`
  ADD PRIMARY KEY (`ID`),
  ADD KEY `ID_caja` (`ID_caja`);

--
-- Indices de la tabla `ventas`
--
ALTER TABLE `ventas`
  ADD PRIMARY KEY (`ID`),
  ADD KEY `ID_cliente` (`ID_cliente`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `cajas`
--
ALTER TABLE `cajas`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT de la tabla `categorias`
--
ALTER TABLE `categorias`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=17;

--
-- AUTO_INCREMENT de la tabla `clientes`
--
ALTER TABLE `clientes`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- AUTO_INCREMENT de la tabla `compras`
--
ALTER TABLE `compras`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=28;

--
-- AUTO_INCREMENT de la tabla `configuracion`
--
ALTER TABLE `configuracion`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT de la tabla `detalle_cajas`
--
ALTER TABLE `detalle_cajas`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=38;

--
-- AUTO_INCREMENT de la tabla `detalle_compra`
--
ALTER TABLE `detalle_compra`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=77;

--
-- AUTO_INCREMENT de la tabla `detalle_ventas`
--
ALTER TABLE `detalle_ventas`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=48;

--
-- AUTO_INCREMENT de la tabla `medidas`
--
ALTER TABLE `medidas`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT de la tabla `productos`
--
ALTER TABLE `productos`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=81;

--
-- AUTO_INCREMENT de la tabla `proveedor`
--
ALTER TABLE `proveedor`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT de la tabla `usuarios`
--
ALTER TABLE `usuarios`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT de la tabla `ventas`
--
ALTER TABLE `ventas`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=39;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `compras`
--
ALTER TABLE `compras`
  ADD CONSTRAINT `compras_ibfk_1` FOREIGN KEY (`ID_proveedor`) REFERENCES `proveedor` (`ID`);

--
-- Filtros para la tabla `detalle_cajas`
--
ALTER TABLE `detalle_cajas`
  ADD CONSTRAINT `detalle_cajas_ibfk_1` FOREIGN KEY (`ID_usuario`) REFERENCES `usuarios` (`ID`);

--
-- Filtros para la tabla `detalle_compra`
--
ALTER TABLE `detalle_compra`
  ADD CONSTRAINT `detalle_compra_ibfk_1` FOREIGN KEY (`ID_compra`) REFERENCES `compras` (`ID`),
  ADD CONSTRAINT `detalle_compra_ibfk_2` FOREIGN KEY (`ID_producto`) REFERENCES `productos` (`ID`);

--
-- Filtros para la tabla `detalle_ventas`
--
ALTER TABLE `detalle_ventas`
  ADD CONSTRAINT `detalle_ventas_ibfk_1` FOREIGN KEY (`ID_producto`) REFERENCES `productos` (`ID`),
  ADD CONSTRAINT `detalle_ventas_ibfk_2` FOREIGN KEY (`ID_venta`) REFERENCES `ventas` (`ID`);

--
-- Filtros para la tabla `productos`
--
ALTER TABLE `productos`
  ADD CONSTRAINT `productos_ibfk_1` FOREIGN KEY (`ID_proveedor`) REFERENCES `proveedor` (`ID`),
  ADD CONSTRAINT `productos_ibfk_2` FOREIGN KEY (`ID_categoria`) REFERENCES `categorias` (`ID`),
  ADD CONSTRAINT `productos_ibfk_3` FOREIGN KEY (`ID_medida`) REFERENCES `medidas` (`ID`);

--
-- Filtros para la tabla `usuarios`
--
ALTER TABLE `usuarios`
  ADD CONSTRAINT `usuarios_ibfk_1` FOREIGN KEY (`ID_caja`) REFERENCES `cajas` (`ID`);

--
-- Filtros para la tabla `ventas`
--
ALTER TABLE `ventas`
  ADD CONSTRAINT `ventas_ibfk_1` FOREIGN KEY (`ID_cliente`) REFERENCES `clientes` (`ID`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
