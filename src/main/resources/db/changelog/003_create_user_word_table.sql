CREATE TABLE user_word(
    id serial PRIMARY KEY,
    user_id integer REFERENCES users(id),
    word_id integer REFERENCES words(id)
)