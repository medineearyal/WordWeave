-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Apr 19, 2025 at 01:48 PM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `wordweave`
--

-- --------------------------------------------------------

--
-- Table structure for table `blog`
--

CREATE TABLE `blog` (
  `blog_id` int(11) NOT NULL,
  `content` text NOT NULL,
  `title` varchar(255) NOT NULL,
  `publish_date` date NOT NULL,
  `author_id` int(11) NOT NULL,
  `updated_at` date NOT NULL DEFAULT current_timestamp(),
  `image` varchar(255) DEFAULT NULL,
  `is_trending` tinyint(1) NOT NULL DEFAULT 0,
  `views` int(11) NOT NULL DEFAULT 0,
  `is_draft` tinyint(1) NOT NULL DEFAULT 1
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `blog`
--

INSERT INTO `blog` (`blog_id`, `content`, `title`, `publish_date`, `author_id`, `updated_at`, `image`, `is_trending`, `views`, `is_draft`) VALUES
(2, 'There’s something magical about that first sip of coffee in the morning. The warmth, the aroma, the quiet moment before the day begins. It’s not just caffeine—it’s a ritual of comfort. ☕✨', 'The Joy of Morning Coffee', '2025-04-17', 18, '2025-04-18', '/images/istockphoto-1859646927-612x612.jpg', 1, 7, 0),
(3, 'Nam aliquip aut aut ', 'Berk Wheeler', '1985-02-16', 18, '2025-04-13', '/images/ba1eb7dd504655cb72c1b96efc284487.jpg', 1, 1, 0),
(4, 'Eos consequuntur do', 'Theodore Tran', '1983-09-22', 18, '2025-04-13', '/images/6f995bf926675f1fbaa5a40078d4b348.jpg', 1, 1, 0),
(5, 'Atque non pariatur ', 'Kirestin Lamb', '1971-05-13', 18, '2025-04-13', '/images/a35103960c078fe56129c06c997dfa76.jpg', 1, 1, 0),
(6, 'Qui ad velit aspern', 'McKenzie Conner', '1988-05-21', 18, '2025-04-13', '/images/download.png', 1, 1, 0),
(7, 'Ut provident sint s', 'Grant Randall', '1975-03-07', 18, '2025-04-13', '/images/e4a30cec56f003a30ecdf8a395d67075.jpg', 1, 2, 0),
(8, 'Nam quae nulla labor', 'Sade Johnson', '1997-06-14', 18, '2025-04-13', '/images/1a006d8518a85234dc1d912439ba9916.jpg', 0, 1, 0),
(9, 'Repudiandae sapiente', 'Malachi Mathews', '1973-04-28', 18, '2025-04-13', '/images/f2dc65ea54d89c5c82058289ac733cb5.jpg', 0, 1, 0),
(10, 'Magni culpa duis tem', 'Idola Franks', '1987-11-01', 18, '2025-04-13', '/images/5513ad878bce191ba3eba7c1078300ff.jpg', 0, 1, 0),
(11, 'Autem non autem pari', 'Jillian Wheeler', '1991-03-10', 18, '2025-04-13', '/images/USE CASE DIAGRAM-Page-3.drawio (1).png', 0, 1, 0),
(12, 'Qui est proident ad', 'Blaze Key', '2023-08-28', 18, '2025-04-13', '/images/USE CASE DIAGRAM-Page-10.drawio.png', 0, 1, 0),
(13, 'Officia ullam dolore', 'Arsenio Olsen', '1979-08-06', 18, '2025-04-13', '/images/USE CASE DIAGRAM-Page-10.drawio (2).png', 0, 1, 0),
(14, 'Consequatur praesent', 'September Christensen', '1994-04-11', 18, '2025-04-13', '/images/USE CASE DIAGRAM-Page-10.drawio.png', 0, 1, 0);

-- --------------------------------------------------------

--
-- Table structure for table `blog_approval`
--

CREATE TABLE `blog_approval` (
  `blog_id` int(11) NOT NULL,
  `approved_by` int(11) NOT NULL,
  `approval_date` date NOT NULL,
  `approve_status` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `blog_category`
--

CREATE TABLE `blog_category` (
  `blog_id` int(11) NOT NULL,
  `category_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `blog_category`
--

INSERT INTO `blog_category` (`blog_id`, `category_id`) VALUES
(13, 5),
(13, 8),
(12, 5),
(12, 8),
(11, 5),
(11, 6),
(11, 7),
(14, 6),
(8, 6),
(10, 5),
(10, 7),
(10, 8),
(2, 6),
(3, 6);

-- --------------------------------------------------------

--
-- Table structure for table `category`
--

CREATE TABLE `category` (
  `category_id` int(11) NOT NULL,
  `name` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `category`
--

INSERT INTO `category` (`category_id`, `name`) VALUES
(5, 'Fashion'),
(6, 'Entertainment'),
(7, 'Health'),
(8, 'Beauty');

-- --------------------------------------------------------

--
-- Table structure for table `contact_message`
--

CREATE TABLE `contact_message` (
  `message_id` int(11) NOT NULL,
  `author_id` int(11) NOT NULL,
  `message_date` date NOT NULL,
  `sender_email` varchar(255) NOT NULL,
  `message_text` text NOT NULL,
  `sender_name` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `favorite_blogs`
--

CREATE TABLE `favorite_blogs` (
  `user_id` int(11) NOT NULL,
  `blog_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `favorite_blogs`
--

INSERT INTO `favorite_blogs` (`user_id`, `blog_id`) VALUES
(26, 2);

-- --------------------------------------------------------

--
-- Table structure for table `roles`
--

CREATE TABLE `roles` (
  `role_id` int(11) NOT NULL,
  `name` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `roles`
--

INSERT INTO `roles` (`role_id`, `name`) VALUES
(1, 'admin'),
(2, 'user'),
(3, 'moderator');

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE `user` (
  `user_id` int(11) NOT NULL,
  `username` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `fullname` varchar(255) NOT NULL,
  `role_id` int(11) NOT NULL,
  `profile_picture` varchar(255) DEFAULT NULL,
  `bio` varchar(255) DEFAULT NULL,
  `created_at` date NOT NULL DEFAULT current_timestamp(),
  `is_approved` tinyint(1) NOT NULL DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`user_id`, `username`, `email`, `password`, `fullname`, `role_id`, `profile_picture`, `bio`, `created_at`, `is_approved`) VALUES
(18, 'nygax', 'hequroteci@mailinator.com', 'jigd3EcmazisBH0I3yfHH4O5q4KXuDrQ/QPjpXeZCqb+wgeDTNzrwggIG3busSBX+HsXW64=', 'Shafira Bowers', 2, '/images/USE CASE DIAGRAM-Page-10.drawio.png', '', '2025-04-19', 0),
(19, 'bowilicyd', 'qysecem@mailinator.com', 'VhFtqO45g+iPHWkUEOd2KLsCdmO/s6ArXiWXt1J96WQcu+TQI5wdFaOAVnK3ADDJuv7PEy4=', 'Ira Wheeler', 2, '/images/5513ad878bce191ba3eba7c1078300ff.jpg', '', '2025-04-19', 0),
(24, 'admin', 'admin@admin.com', 'iCl++cTEpJfpkv9BQvXU+cJQrx9aQs6IgFnO+9ctJ7WTB4DzhK67q5rvTM+LW3C2TG/coGI=', 'admin', 1, '/images/medinee.jpg', NULL, '2025-04-19', 0),
(25, 'moderator', 'junydukar@mailinator.com', 'Admin@123', 'Ivy Madden', 3, 'medinee.jpg', '', '2025-04-19', 0),
(26, 'medi', 'medineearyal@gmail.com', 'FW7ZQT7OIAEgLrh7Hh0cPcT9mSHs+VMA/0+g/E8jN9mFKP50kLWxKBUGDzoPajuuNAXTROU=', 'Medinee', 3, '/images/medinee.jpg', NULL, '2025-04-19', 0);

-- --------------------------------------------------------

--
-- Table structure for table `user_blog`
--

CREATE TABLE `user_blog` (
  `user_id` int(11) NOT NULL,
  `blog_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `blog`
--
ALTER TABLE `blog`
  ADD PRIMARY KEY (`blog_id`),
  ADD KEY `fk_author` (`author_id`);

--
-- Indexes for table `blog_approval`
--
ALTER TABLE `blog_approval`
  ADD KEY `fk_blog_approve` (`blog_id`),
  ADD KEY `fk_user_approve` (`approved_by`);

--
-- Indexes for table `blog_category`
--
ALTER TABLE `blog_category`
  ADD KEY `fk_m2m_blog` (`blog_id`),
  ADD KEY `fk_m2m_category` (`category_id`);

--
-- Indexes for table `category`
--
ALTER TABLE `category`
  ADD PRIMARY KEY (`category_id`);

--
-- Indexes for table `contact_message`
--
ALTER TABLE `contact_message`
  ADD PRIMARY KEY (`message_id`),
  ADD KEY `fk_author_contact` (`author_id`);

--
-- Indexes for table `favorite_blogs`
--
ALTER TABLE `favorite_blogs`
  ADD KEY `fk_favorite_user` (`user_id`),
  ADD KEY `fk_favorite_blog` (`blog_id`);

--
-- Indexes for table `roles`
--
ALTER TABLE `roles`
  ADD PRIMARY KEY (`role_id`);

--
-- Indexes for table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`user_id`),
  ADD KEY `fk_user_role` (`role_id`);

--
-- Indexes for table `user_blog`
--
ALTER TABLE `user_blog`
  ADD KEY `fk_user_blog` (`blog_id`),
  ADD KEY `fk_user_author` (`user_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `blog`
--
ALTER TABLE `blog`
  MODIFY `blog_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=15;

--
-- AUTO_INCREMENT for table `category`
--
ALTER TABLE `category`
  MODIFY `category_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- AUTO_INCREMENT for table `contact_message`
--
ALTER TABLE `contact_message`
  MODIFY `message_id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `roles`
--
ALTER TABLE `roles`
  MODIFY `role_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `user`
--
ALTER TABLE `user`
  MODIFY `user_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=27;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `blog`
--
ALTER TABLE `blog`
  ADD CONSTRAINT `fk_author` FOREIGN KEY (`author_id`) REFERENCES `user` (`user_id`);

--
-- Constraints for table `blog_approval`
--
ALTER TABLE `blog_approval`
  ADD CONSTRAINT `fk_user_approve` FOREIGN KEY (`approved_by`) REFERENCES `user` (`user_id`);

--
-- Constraints for table `blog_category`
--
ALTER TABLE `blog_category`
  ADD CONSTRAINT `fk_m2m_blog` FOREIGN KEY (`blog_id`) REFERENCES `blog` (`blog_id`),
  ADD CONSTRAINT `fk_m2m_category` FOREIGN KEY (`category_id`) REFERENCES `category` (`category_id`);

--
-- Constraints for table `contact_message`
--
ALTER TABLE `contact_message`
  ADD CONSTRAINT `fk_author_contact` FOREIGN KEY (`author_id`) REFERENCES `user` (`user_id`);

--
-- Constraints for table `favorite_blogs`
--
ALTER TABLE `favorite_blogs`
  ADD CONSTRAINT `fk_favorite_blog` FOREIGN KEY (`blog_id`) REFERENCES `blog` (`blog_id`),
  ADD CONSTRAINT `fk_favorite_user` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`);

--
-- Constraints for table `user`
--
ALTER TABLE `user`
  ADD CONSTRAINT `fk_user_role` FOREIGN KEY (`role_id`) REFERENCES `roles` (`role_id`);

--
-- Constraints for table `user_blog`
--
ALTER TABLE `user_blog`
  ADD CONSTRAINT `fk_user_author` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`),
  ADD CONSTRAINT `fk_user_blog` FOREIGN KEY (`blog_id`) REFERENCES `blog` (`blog_id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
