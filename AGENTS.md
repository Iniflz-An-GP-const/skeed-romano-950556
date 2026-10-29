# Skeed Romano

This project was generated from a donor template by TemplateForge.
Application type: **Sport Quiz** (Jetpack Compose, Koin, Room).

- Package: `goldrush.skeedromano.official`
- Project name: `Skeed Romano`

## Where the content lives

- `app/src/main/res/values/strings.xml` — English strings, the primary
  locale. `app/src/main/res/values-<lang>/strings.xml` — the same
  resources translated into de, es, fr, hi, id, pt and ar.
- The Kotlin data files sit in the `data` package of
  `goldrush.skeedromano.official`, under `app/src/main/java/`:
  - `data/repository/TopicRepository.kt` — the quiz content. It ships
    empty: a list of `Topic` objects, each holding its `Question`
    objects, each holding its `Answer` objects.
  - `data/model/Topic.kt`, `data/model/Question.kt` and
    `data/model/Answer.kt` — the shape of those three objects.
  - `data/model/Localized.kt` — `forCurrentLocale()`, the lookup every
    `texts` map is read through. Nothing to fill in here.

## Data invariants

- English is the primary language. `values/strings.xml` and the `"en"`
  entry of every `texts` map are in English, even when the task's
  input is given in another language.
- A `texts` map is keyed by two-letter language code. The `"en"` entry
  is mandatory — it is the fallback the app reads when the device
  language has no entry of its own; the other languages of this
  project follow it: "de", "es", "fr", "hi", "id", "pt", "ar". A topic, a question and an
  answer each hold one text per language.
- `Topic.id` is unique among topics; `Question.id` is unique across
  the whole repository; `Answer.id` is unique within its question.
- Every question has exactly 4 answers, and `rightAnswerId` equals the
  `id` of exactly one of them.
- Answers are shown in the order they are written (only questions are
  shuffled at runtime), so the position of the right answer is part of
  the content: keep it varied.
- Questions are real, verifiable sport facts with one unambiguous
  correct answer, and no question repeats across topics. A translation
  asks the same question and marks the same answer right — it is one
  question in several languages, not several questions.
- Texts stay short, in every language. A player has 15 seconds per
  question, answer buttons have a fixed size, and a topic name is
  shown on a card: one to three words.
- Keep every format placeholder (`%1$d`, `%2$d`, `%1$s`, `%%`) and the
  `plurals` structure. A translation keeps the resource name and the
  placeholders of the English string, in the same number; their order
  changes only if the language needs it, and then in positional form
  (`%2$s` before `%1$s`). A translated `plurals` keeps its `one` and
  `other` items, and may add the quantity forms its own language needs
  (`few` and `many` in Arabic).
- A right-to-left language (Arabic, Hebrew, Persian, Urdu) reorders a
  run of digits and separators sitting inside its own text: `%1$d/%2$d`
  can reach the screen as `8/3`, and `#%1$s · %2$s` with its two parts
  swapped. Keep the placeholders in the order the English string gives
  them, and wrap each such run in left-to-right marks —
  `\u200E%1$d/%2$d\u200E` — which are invisible and change nothing else.
  It applies to every digit-and-symbol string: a score, a percentage, a
  counter, a progress pair, anything joined by `#`, `/`, `×` or `·`.
- `about_rules` describes how the app actually works (15 seconds per
  question and a timeout counts as a wrong answer; an answer locks in
  at once and the correct one is revealed before the next question;
  Duo mode alternates turns; the best score is tracked per quiz length
  and only a higher score replaces it; high scores are recorded only
  in Solo mode; chosen topics are shuffled together). Change it only
  when the game behaviour changes, and keep every fact and the same
  number of items in every language.
- Resources marked `translatable="false"` — the app name, the
  developer name, the contact link — stay exactly as they are and are
  not copied into `values-<lang>/`; neither are empty resources.
- In any `strings.xml`, escape an apostrophe as `\'` and an ampersand
  as `&amp;`. A bare apostrophe is still well-formed XML, so a plain XML
  check will not catch it — aapt2 will, by failing the build.

## Text has to fit the layout

- Rephrase a string resource when it reads as generic and text written
  for a sport quiz would fit better. One that already reads naturally
  stays as it is — the goal is a cohesive result, not rewriting for
  its own sake. Pure interface chrome (Back, Cancel, Exit, Shuffle)
  has no topic to fit: leave it alone.
- A replacement keeps roughly the length of the text it replaces — the
  screens were built around those lengths, and longer text wraps badly
  or gets truncated on a phone. The same goes for a translation
  against its English original.
- Buttons, tabs, filter chips, labels and titles stay short, usually
  one to three words: do not turn a label into a sentence, and do not
  add marketing filler, adjective chains, or the company name where
  the original had none.

## Working in this repo

- Keep changes scoped to what the current task asks for — this is a
  freshly generated project, not a hand-maintained one, so do not
  "clean up" unrelated files.
- Preserve the existing package structure and naming conventions found
  in the generated code instead of introducing new patterns.
- Do not modify build/gradle configuration, project name, package
  name, or app id — those were set intentionally during generation.
- Screen variants live in `v_*` packages, and the theme and typography
  in `ui/theme`; they were selected during generation and are not
  content.
- Do not run Gradle builds, dependency downloads, Android lint, or
  emulator tests. The project is published as-is after your work.
  Validate only the modified data and resource files with lightweight
  local checks.
- Do not change business logic or UI code — limit changes strictly to
  the data described above (the quiz content, the string resources and
  their translations). Creating the `values-<lang>/strings.xml` files
  is part of that data.
- When filling in placeholder data, match the style and format already
  used elsewhere in the file.
