CREATE TABLE IF NOT EXISTS lessons (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    chapter_id BIGINT NOT NULL,
    title VARCHAR(255) NOT NULL,
    content TEXT NULL,
    order_index INT NULL,
    video_url VARCHAR(500) NULL,
    created_at DATETIME(6) NULL,
    updated_at DATETIME(6) NULL,
    CONSTRAINT fk_lessons_chapter
        FOREIGN KEY (chapter_id) REFERENCES chapters(id)
        ON DELETE CASCADE
);

CREATE INDEX idx_lessons_chapter_id ON lessons(chapter_id);
CREATE INDEX idx_lessons_order_index ON lessons(order_index);

-- Seed one default lesson per chapter if lesson #1 does not exist yet.
INSERT INTO lessons (chapter_id, title, content, order_index, video_url, created_at, updated_at)
SELECT
    ch.id,
    CONCAT('Lesson 1 - ', ch.title),
    'Auto-seeded introduction lesson.',
    1,
    NULL,
    NOW(6),
    NOW(6)
FROM chapters ch
WHERE NOT EXISTS (
    SELECT 1
    FROM lessons l
    WHERE l.chapter_id = ch.id
      AND l.order_index = 1
);
