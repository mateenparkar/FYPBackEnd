INSERT INTO BookGenres VALUES (1, "Fiction");
INSERT INTO BookGenres VALUES (2, "Romance");

INSERT INTO Authors VALUES (1, "Derek Landy");
INSERT INTO Authors VALUES (2, "Julie Quinn");

INSERT INTO Books VALUES (1, "Skulduggery Pleasant", 1, '2007-04-03', 1, "The story follows the characters Skulduggery Pleasant — a skeleton, sorcerer and detective — and his partner Stephanie Edgley, who also goes by Valkyrie Cain. They and their numerous magic-wielding allies try to prevent the necromancer Nefarian Serpine from unleashing a powerful weapon called The Sceptre of Ancients.", "https://ibb.co/fH6h1wk");
UPDATE Books SET cover_image_url = "https://i.ibb.co/gJq5jKS/Skulduggery-Pleasant-cover-2.webp" WHERE book_id=1;
INSERT INTO Books VALUES (2, "The Duke and I", 2, '2000-01-05', 2,"The Duke and I is a 2000 historical romance novel written by Julia Quinn, first published by Avon. It is the first novel of Quinn's series of Regency romances about the Bridgerton siblings and tells the story of Daphne, the fourth child and eldest daughter of the family.", "https://i.ibb.co/Ldwzsrh/The-Duke-and-I-Cover.jpg");