UPDATE astronauts
SET image = regexp_replace(image, '^https?://[^/]+', '')
WHERE image IS NOT NULL AND image ~ '^https?://[^/]+/assets/astronauts/';