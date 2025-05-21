-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: May 21, 2025 at 05:17 PM
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
  `updated_at` datetime NOT NULL DEFAULT current_timestamp(),
  `image` varchar(255) DEFAULT NULL,
  `is_trending` tinyint(1) NOT NULL DEFAULT 0,
  `views` int(11) NOT NULL DEFAULT 0,
  `is_draft` tinyint(1) NOT NULL DEFAULT 1
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `blog`
--

INSERT INTO `blog` (`blog_id`, `content`, `title`, `publish_date`, `author_id`, `updated_at`, `image`, `is_trending`, `views`, `is_draft`) VALUES
(2, 'Waking up to the aroma of freshly brewed coffee is a ritual cherished by many. Beyond its invigorating taste and warmth, coffee holds a special place in our daily routines, offering more than just a caffeine boost. Let\'s explore the delightful world of morning coffee and why it\'s more than just a beverage.\r\n\r\nThe process begins with the anticipation of that first sip. Whether you brew your coffee at home or grab it on the go, the ritual of preparing your perfect cup sets the tone for the day ahead. Grinding the beans, listening to the drip of the coffee maker, or watching the barista craft your drink—each step builds anticipation and creates a moment of calm before the day begins.\r\n\r\nMorning coffee often serves as a brief escape from the hustle and bustle of life. It\'s a time to sit quietly, gather your thoughts, and mentally prepare for the day. This moment of solitude can be meditative, allowing you to reflect on goals, plan your schedule, or simply enjoy the quiet before the world wakes up.\r\n\r\nThe first sip of coffee is an experience like no other. The warmth spreads through your body, awakening your senses and providing an instant energy boost. Whether you prefer a strong espresso or a creamy latte, that initial taste is a burst of flavor that jump-starts your morning.\r\n\r\nBeyond its personal benefits, coffee often brings people together. Whether it\'s catching up with friends over a cup, meeting colleagues for a brainstorming session, or enjoying a morning chat with family members, coffee breaks foster connections and create shared moments.\r\n \r\nStudies suggest that moderate coffee consumption offers health benefits, from improved cognitive function to reduced risk of certain diseases. Moreover, the act of enjoying coffee can contribute to mental well-being, providing comfort and a sense of familiarity.\r\n\r\n\r\nCoffee is more than a beverage; it\'s a global culture with diverse traditions and rituals. From Italian espresso bars to cozy neighborhood cafes, each culture adds its unique flavor to the coffee experience. Exploring different coffee styles and brewing methods allows us to appreciate this rich cultural tapestry.\r\n \r\nThe joy of morning coffee extends beyond its taste and stimulating effects. It\'s about savoring moments of tranquility, fostering connections, and embracing a ritual that enhances our daily lives. Whether you\'re a devoted coffee enthusiast or a casual drinker, the ritual of morning coffee offers a small but significant pleasure that can brighten your day.', 'The Joy of Morning Coffee', '2025-05-04', 26, '2025-05-20 11:13:38', '/images/e04afbbb95426d86cb34b31bcf9f1571.jpg', 1, 41, 0),
(3, ' In recent years, nail extensions have surged in popularity, transforming from a niche beauty trend into a mainstream fashion statement. This phenomenon is driven by several factors that highlight why nail extensions are not just a passing trend but a growing industry.\r\n\r\nFashion Forward: Nail extensions offer individuals a canvas to express their creativity and personal style. From intricate designs to bold colors, they provide a way to complement outfits and make a fashion statement that goes beyond clothing.\r\n\r\nEnhanced Aesthetics: For many, nail extensions are a means to achieve longer, stronger nails that may be difficult to grow naturally. This enhancement contributes to a polished appearance, boosting confidence and self-esteem.\r\n\r\nVersatility in Design: With advancements in technology and materials, nail technicians can create diverse looks, ranging from classic French tips to avant-garde designs. This versatility appeals to a broad audience seeking unique, customizable beauty options.', 'Why Nail Extension is Growing?', '2025-05-02', 18, '2025-05-20 11:13:08', '/images/31a4b66e23d2b5ad689e4854f8cbc650.jpg', 1, 14, 0),
(4, ' If you’re looking to upgrade your sneaker game, this month’s Nike lineup offers something for everyone—from runners to gym warriors, street-style lovers to casual walkers. Here are the top Nike shoes you should check out this month:\r\n\r\n🔥 1. Nike Air Max 270\r\nWith its bold heel bubble and ultra-cushioned feel, the Air Max 270 continues to dominate both comfort and style charts. Great for casual wear with just the right touch of sporty flair.\r\n\r\n🏃‍♂️ 2. Nike ZoomX Vaporfly Next% 3\r\nMade for serious runners, this shoe is all about speed and efficiency. The latest version offers a lighter build, responsive cushioning, and carbon-fiber propulsion—perfect for long-distance goals.\r\n\r\n🏋️ 3. Nike Metcon 9\r\nA gym favorite! Designed for heavy lifting and high-intensity workouts, Metcon 9 offers excellent grip, stability, and a sleek design that’s gym-floor ready.\r\n\r\n👟 4. Nike Dunk Low Retro\r\nThe Dunks are having a moment—and for good reason. These retro classics come in fresh colorways every month and strike the perfect balance between heritage and hype.\r\n\r\n💨 5. Nike Pegasus 41\r\nThe newest release in the Pegasus series continues its legacy of comfort and daily running support. With an upgraded midsole and lightweight feel, it’s perfect for both beginners and seasoned joggers.\r\n\r\n🌈 6. Nike Air Force 1 \'07 LV8\r\nTimeless never goes out of style. The AF1 gets a fresh twist this month with premium leather and bold detailing—ideal for casual streetwear or dressing up your basics.', 'Best Nike Shoes This Month – Top Picks for Style', '2025-05-18', 18, '2025-05-20 11:12:18', '/images/4c97d8b92aa76faca4e3707b358b46ec.jpg', 1, 27, 0),
(5, ' Chetan Bhagat’s One Indian Girl isn’t just another bestseller—it’s a bold, thought-provoking narrative that challenges stereotypes and gives a voice to the modern Indian woman. If you haven’t read it yet, here’s why you absolutely should.\r\n\r\n👩‍💼 A Relatable Modern Woman\r\nRadhika Mehta, the protagonist, is a high-achieving investment banker who’s made it in a man’s world. But behind her successful career lies a tug-of-war between societal expectations and her personal desires. Her journey feels real, raw, and deeply relatable—especially for women juggling ambition, love, and tradition.\r\n\r\n🧠 Breaking Stereotypes Boldly\r\nThe book addresses taboos rarely spoken about openly in Indian households—feminism, relationships, self-worth, and the pressure of being the \"ideal girl.\" It doesn\'t preach but instead opens up space for reflection.\r\n\r\n💬 Simple Yet Powerful Storytelling\r\nBhagat’s signature style—straightforward, fast-paced, and emotional—makes the story accessible even to non-regular readers. The book reads like a conversation, pulling you in from the first page.\r\n\r\n🔄 Multiple Perspectives\r\nWhat makes One Indian Girl unique is how it explores different sides of the same story. Radhika\'s internal voice often contradicts her outward decisions, giving readers a peek into the layered complexity of modern women’s lives.\r\n\r\n💥 Final Word\r\nOne Indian Girl is more than just fiction—it’s a reflection of the changing face of Indian society. Whether you\'re a woman finding your voice or someone trying to understand the pressures women face, this book is a powerful and essential read.\r\n\r\nGive it a shot—you might just see the world a little differently. 💖', 'One Indian Girl Is a Must Read', '2025-04-12', 18, '2025-05-20 11:09:52', '/images/75e16447040f6e8858f27490c24e962c.jpg', 1, 8, 0),
(6, 'Gold lockets are making a bold comeback in 2025, merging sentimental charm with modern aesthetics. Whether you\'re shopping for a statement piece or a subtle everyday accessory, this year’s trends offer something truly special.\r\n\r\n💛 1. Miniature & Minimalist Lockets\r\nLess is more. Tiny gold lockets with sleek, smooth surfaces are perfect for layering or solo wear. These dainty designs are especially popular among Gen Z and minimalists.\r\n\r\n🧬 2. Personalized Engravings\r\nCustom engravings are all the rage. Names, initials, dates, or even QR codes with secret messages are turning lockets into truly personal pieces.\r\n\r\n🌙 3. Celestial Motifs\r\nThink stars, moons, and zodiac signs etched or enameled on lockets. This mystical aesthetic is trending big, especially in gold finishes paired with gemstones or mother-of-pearl.\r\n\r\n💎 4. Vintage-Inspired Designs\r\nHeirloom-style lockets with intricate filigree, floral engravings, or antique finishes are being reimagined by top jewelers. They\'re great for layering or wearing on velvet or rope chains.\r\n\r\n👩‍👧 5. Memory Lockets\r\nLockets with tiny photo frames or scent compartments are making an emotional comeback. These pieces carry personal meaning—perfect for gifts or keepsakes.', ' Gold Locket Trends to Watch in 2025', '2025-05-20', 18, '2025-05-20 11:16:07', '/images/6a8c90d271c27fe50b2365a529f17c51.jpg', 1, 3, 0),
(7, ' In a world obsessed with health trends, there’s one timeless habit that continues to deliver results: drinking water. Specifically, about 2 liters (or 8 glasses) a day. It may sound simple, but this daily habit can do wonders for your body and mind.\r\n\r\n🚰 1. Boosts Energy and Fights Fatigue\r\nEver feel tired for no reason? You might just be dehydrated. Water helps transport oxygen and nutrients throughout your body, keeping your energy levels stable and your brain sharp.\r\n\r\n🌟 2. Clears Up Your Skin\r\nConsistent water intake helps flush out toxins, leading to clearer and healthier skin. It can even reduce acne and give your complexion a natural glow.\r\n\r\n🍽️ 3. Aids in Digestion and Weight Management\r\nWater improves digestion, prevents constipation, and helps you feel full. Drinking water before meals can curb overeating, making it easier to manage weight.\r\n\r\n🧠 4. Improves Mood and Focus\r\nEven mild dehydration can affect your mood, memory, and concentration. Keeping hydrated helps you stay calm, focused, and productive throughout the day.', 'Drinking 2 Liters of Water a Day is a Game-Changer', '2025-04-10', 18, '2025-05-20 11:08:09', '/images/d9cfb329b9b48737bf12c2f20c3cd3eb.jpg', 1, 5, 0),
(8, ' Nestled in the heart of the Kathmandu Valley, Bhaktapur—also known as Bhadgaon—stands as a living museum of Nepalese heritage, art, and tradition. Often referred to as the \"City of Devotees,\" this ancient city is renowned for its meticulously preserved architecture, vibrant festivals, and deep-rooted cultural identity.\r\n\r\nBhaktapur was once the capital of the Malla Kingdom and played a vital role in shaping the valley’s medieval history. Today, the city retains its historical charm through its red brick buildings, narrow alleyways, and majestic temples, many of which date back to the 12th century. Bhaktapur Durbar Square, a UNESCO World Heritage Site, remains the cultural heart of the city. Here, visitors can marvel at iconic landmarks such as the 55-Window Palace, Vatsala Temple, Nyatapola Temple, and countless stone sculptures and woodcarvings that reflect the pinnacle of Newar craftsmanship.', ' Bhaktapur: A City of Culture', '2025-04-02', 19, '2025-05-20 11:02:41', '/images/6f8674a49a11d4f58e1e99db3de83733.jpg', 0, 2, 0),
(9, 'From neon-lit cities to serene temples, Japan is a captivating blend of tradition and innovation. Whether you\'re chasing cherry blossoms or curious about samurai history, this beautiful island nation has something for everyone. Here are the top 10 places you simply can\'t miss on your next trip to Japan:\r\n\r\n1. Tokyo – The City That Never Sleeps\r\nJapan’s capital is a buzzing metropolis where futuristic tech meets ancient shrines. Explore Shibuya Crossing, Tokyo Skytree, Akihabara, and traditional neighborhoods like Asakusa.\r\n\r\n2. Kyoto – Heart of Tradition\r\nOnce the imperial capital, Kyoto is packed with temples, geishas, and zen gardens. Don\'t miss the Fushimi Inari Shrine, Arashiyama Bamboo Grove, and the beautiful Kinkaku-ji (Golden Pavilion).\r\n\r\n3. Osaka – Foodie Heaven\r\nKnown for its vibrant street food and nightlife, Osaka serves up takoyaki, okonomiyaki, and more. Stroll through Dotonbori, visit Osaka Castle, and shop in Shinsaibashi.\r\n\r\n4. Nara – Where Deer Roam Free\r\nJust a train ride from Kyoto, Nara Park is home to over a thousand friendly deer and the impressive Todai-ji Temple, which houses a giant Buddha statue.\r\n\r\n5. Hokkaido – Nature\'s Playground\r\nFamous for its ski resorts, hot springs, and seafood, Hokkaido is ideal for nature lovers. Visit in winter for snow festivals or in summer for colorful flower fields.', 'Top 5 Activities to do in Japan', '2025-05-06', 32, '2025-05-20 11:05:48', '/images/225cfea0030b121651fb8a788467273e.jpg', 0, 2, 0),
(10, ' Thangka painting is a traditional form of Tibetan Buddhist art that has flourished in Nepal, particularly among the Newar and Tibetan communities. These sacred scroll paintings are rich in iconography and primarily serve as teaching tools, devotional images, or aids for meditation.\r\n\r\nReligious and Cultural Significance\r\nDeeply rooted in Vajrayana Buddhism, Thangka paintings depict deities, mandalas, and scenes from the life of the Buddha. In Nepal, they are used during religious ceremonies, spiritual instruction, and ritual practices, often carried by monks or displayed in monasteries and homes.\r\n\r\nTechniques and Materials\r\nThe creation of a Thangka is a meticulous process involving natural pigments, gold leaf, and hand-woven cotton or silk. Artists follow precise geometric and symbolic guidelines, as the proportions and postures of deities must adhere to strict canonical standards passed down through generations.', 'Thangka Painting in Nepal ', '2025-04-01', 26, '2025-05-20 11:00:37', '/images/38feebdb0c32af2148b52938b3c080bf.jpg', 0, 2, 0),
(11, 'Autem non autem pari', 'Jillian Wheeler', '1991-03-10', 18, '2025-04-13 00:00:00', '/images/USE CASE DIAGRAM-Page-3.drawio (1).png', 0, 1, 1),
(12, 'Qui est proident ad', 'Blaze Key', '2023-08-28', 18, '2025-04-13 00:00:00', '/images/USE CASE DIAGRAM-Page-10.drawio.png', 0, 1, 1),
(13, 'Officia ullam dolore', 'Arsenio Olsen', '1979-08-06', 18, '2025-04-13 00:00:00', '/images/USE CASE DIAGRAM-Page-10.drawio (2).png', 0, 1, 1),
(14, 'Consequatur praesent', 'September Christensen', '1994-04-11', 18, '2025-04-13 00:00:00', '/images/USE CASE DIAGRAM-Page-10.drawio.png', 0, 1, 1),
(19, 'Quis culpa laboris n', 'Jasmine Gallagher', '1975-07-25', 26, '2025-04-23 00:00:00', NULL, 0, 0, 1),
(21, 'Totam molestiae dolo', 'Martena Harvey', '2025-05-03', 32, '2025-05-03 00:00:00', '/images/image5.webp', 0, 0, 1),
(22, 'Quae unde ea praesen', 'Cassandra Kidd', '2017-10-22', 32, '2025-05-03 00:00:00', NULL, 0, 0, 1),
(23, 'Autem vel odit et ut', 'Yetta Mccarty', '2020-11-03', 18, '2025-05-03 00:00:00', '/images/average_request_closing_time.png', 0, 0, 1),
(24, 'Velit quos maxime au', 'Kessie Thompson', '2025-05-04', 35, '2025-05-04 01:32:30', NULL, 0, 0, 1),
(25, 'Fuga Quis quis itaq', 'Jack Craig', '1977-01-26', 35, '2025-05-04 01:33:15', NULL, 0, 0, 1),
(30, 'The Cannes Film Festival isn’t just a celebration of cinema—it’s a glamorous global stage, and Bollywood celebrities have made it their runway. Over the years, Indian actors have dazzled at Cannes, representing not just films but also Indian fashion, culture, and star power.\r\n\r\n1. Aishwarya Rai Bachchan – The Eternal Cannes Queen\r\nA regular at Cannes since 2002, Aishwarya Rai has turned heads with every appearance. From elegant gowns to traditional sarees, she has showcased the perfect blend of Indian grace and international flair.\r\n\r\n2. Deepika Padukone – The Global Icon\r\nAs a L\'Oréal ambassador and Cannes jury member, Deepika Padukone brought both poise and power to the red carpet. Her experimental looks and bold fashion choices earned global applause.\r\n\r\n3. Priyanka Chopra – Glamour Meets Confidence\r\nWith her Hollywood crossover, Priyanka’s Cannes appearances were anticipated—and she delivered! Whether walking hand-in-hand with Nick Jonas or attending solo, her looks were both fierce and fabulous.', 'Bollywood Actors at Cannes V2', '2025-05-20', 26, '2025-05-20 14:30:41', NULL, 0, 0, 1);

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
(21, 5),
(22, 5),
(22, 6),
(22, 10),
(23, 6),
(24, 7),
(24, 8),
(24, 10),
(25, 7),
(10, 6),
(8, 6),
(9, 10),
(7, 7),
(5, 6),
(4, 5),
(3, 8),
(2, 7),
(6, 8),
(30, 7);

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
(8, 'Beauty'),
(10, 'Technology');

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

--
-- Dumping data for table `contact_message`
--

INSERT INTO `contact_message` (`message_id`, `author_id`, `message_date`, `sender_email`, `message_text`, `sender_name`) VALUES
(1, 26, '2025-05-20', 'prashna@gmail.com', 'Your blogs are great, love it!', 'Prashna Malla');

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
(26, 2),
(30, 4),
(30, 6),
(32, 8),
(32, 2),
(19, 3),
(26, 3),
(42, 4),
(44, 2),
(44, 3);

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
(18, 'nygax', 'hequroteci@mailinator.com', 'hybdrmNjlrtYyd782xqAK9FW+BnGKZRWLk7tiUKAsGad5UQcE1/fILb/O4TNe4rypLhjDN4=', 'Shafira Bowers', 2, '/images/Rich Fairbank_COF_Color-new-v2-web rz.jpg', '', '2025-04-19', 0),
(19, 'bowilicyd', 'qysecem@mailinator.com', 'VhFtqO45g+iPHWkUEOd2KLsCdmO/s6ArXiWXt1J96WQcu+TQI5wdFaOAVnK3ADDJuv7PEy4=', 'Ira Wheeler', 2, '/images/v3_0601967.jpg', '', '2025-04-19', 0),
(24, 'admin', 'admin@admin.com', 'iCl++cTEpJfpkv9BQvXU+cJQrx9aQs6IgFnO+9ctJ7WTB4DzhK67q5rvTM+LW3C2TG/coGI=', 'admin', 1, '/images/image.png', NULL, '2025-04-19', 0),
(26, 'medi', 'medineearyal@gmail.com', 'Et833DwEe+p6+6IAr7bA9FY3aLOEgtaRS/ZWUSVhoSBTXD75Ig3O/jEIZ9YYDcUBObjcltfThA==', 'Medinee', 2, '/images/Victims-of-identity-theft-at-credit-unions-with-cu-2.0-and-breach-clarity-scaled-1-1536x980.jpg', NULL, '2025-04-19', 0),
(27, 'medinee', 'fifygus@mailinator.com', 'lKd0Uyg25yuHbSJS6EjwSHS0xgWIpDaDeRRy1avqkttj424GXD2SgXxulD+tuVF459fuHm4=', 'Kamal Morin', 2, '/images/sneha.jpg', NULL, '2025-04-19', 0),
(30, 'Pran', 'junydukar@mailinator.com', '93JjjsEMZLLD/wyLyNJsCgJjlUb6mbRhmnx3G/HanO5nu2+jNx8BYRIZ11P1otzgJRJlZCme', 'Pranav', 3, '/images/38feebdb0c32af2148b52938b3c080bf.jpg', NULL, '2025-04-19', 0),
(32, 'dumavufyt', 'ryho@mailinator.com', 'c/T6G9E0OHKT0xtkklu/rCnEbXiG0Pzl8xJx3tJcfA1k/S3qzND3mR9B+QfzRZWnR35zVNY=', 'Cadman David', 2, '/images/grishma.jpg', NULL, '2025-04-20', 0),
(35, 'testing123', 'asd@asd.com', '25JGRaYXGFGJpK4l9wXwPKDWztmLDCdE0ZXHqXriNwuKgyO7T8n3Tn8B/oWK2GSI03gmCxs=', 'aasda dasd', 2, NULL, NULL, '2025-05-04', 0),
(36, 'xycaqyv', 'rosymiqyv@mailinator.com', 'RB5EBmWqiFg/lkopXPvQn9xiD3aXyliWIoSzDlqWeJCItOvWyWT1h7Mb6nUwk4Yps/4w5a8=', '123Mcfarland', 2, '/images/1706113826987.jpg', NULL, '2025-05-04', 0),
(38, 'woruhop', 'myrumam@mailinator.com', 'BpjLjKA1N+lPsR+2HyMFhd+XL8sDQZWbM/rngxyo8AU8hsjBBO2iz/7Dn6I8uezwvcPKAB0=', 'Solomon Holloway', 2, NULL, NULL, '2025-05-04', 0),
(40, 'nirwan', 'nirwan@gmail.com', '+mB4WuOAAGD00SgV82WzYVdu3ig19l+n0Fe/BvW/0vSq1hU+RDryyLM73zq4pmXGR4w7PCqD', 'Nirwan Aryal', 2, NULL, NULL, '2025-05-11', 0),
(42, 'rabina12', 'rabina@gmail.com', 'eVVDViEJXxXP8NBbOL7WBNLabpCI/YlR3d5r6PPKZKsT9PRimCM2x3SD2YPauoCPZa7nk8sN', 'rabina', 2, '/images/USE CASE DIAGRAM-Page-4.drawio (2).png', NULL, '2025-05-11', 0),
(44, 'Adi', 'aditi@gmail.com', 'EVBvdd4V9+VUmwIE/LqY93nR7ixjTlLNmZKCtHcFYGIbRQLz3ClpjzFzfuTCss28wzsXGlM=', 'Aditi Sharma', 2, '/images/f2dc65ea54d89c5c82058289ac733cb5.jpg', NULL, '2025-05-20', 0);

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
  ADD UNIQUE KEY `email` (`email`),
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
  MODIFY `blog_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=31;

--
-- AUTO_INCREMENT for table `category`
--
ALTER TABLE `category`
  MODIFY `category_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=22;

--
-- AUTO_INCREMENT for table `contact_message`
--
ALTER TABLE `contact_message`
  MODIFY `message_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `roles`
--
ALTER TABLE `roles`
  MODIFY `role_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `user`
--
ALTER TABLE `user`
  MODIFY `user_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=46;

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
