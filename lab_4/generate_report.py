from pathlib import Path
from docx import Document
from docx.shared import Pt, Cm
from docx.enum.text import WD_ALIGN_PARAGRAPH
from docx.oxml import OxmlElement
from docx.oxml.ns import qn


BASE = Path(r"C:\Users\Asus\IdeaProjects\java_labs\lab_4")
REPORT = Path(r"C:\Users\Asus\OneDrive\Робочий стіл\університет\2 курс\2 семестр\джава\ІН-204_Юрченко_лр 4.docx")


def style_paragraph(paragraph, *, size=14, bold=False, align=None, spacing=1.5, font="Times New Roman"):
    if align is not None:
        paragraph.alignment = align
    paragraph.paragraph_format.line_spacing = spacing
    paragraph.paragraph_format.space_after = Pt(0)
    paragraph.paragraph_format.space_before = Pt(0)
    for run in paragraph.runs:
        run.font.name = font
        run.font.size = Pt(size)
        run.bold = bold


def add_text(doc, text, *, align=None, size=14, bold=False, spacing=1.5, font="Times New Roman"):
    paragraph = doc.add_paragraph()
    paragraph.add_run(text)
    style_paragraph(paragraph, size=size, bold=bold, align=align, spacing=spacing, font=font)
    return paragraph


def add_blank(doc, count=1):
    for _ in range(count):
        paragraph = doc.add_paragraph("")
        style_paragraph(paragraph)


def add_heading(doc, text, *, align=WD_ALIGN_PARAGRAPH.LEFT):
    paragraph = doc.add_paragraph()
    paragraph.add_run(text)
    style_paragraph(paragraph, bold=True, align=align, spacing=1.5)
    return paragraph


def set_table_borders_none(table):
    tbl_pr = table._tbl.tblPr
    borders = OxmlElement("w:tblBorders")
    for edge in ("top", "left", "bottom", "right", "insideH", "insideV"):
        element = OxmlElement(f"w:{edge}")
        element.set(qn("w:val"), "nil")
        borders.append(element)
    tbl_pr.append(borders)


def fill_cell(cell, text, *, bold=False, align=WD_ALIGN_PARAGRAPH.LEFT):
    cell.text = ""
    paragraph = cell.paragraphs[0]
    paragraph.alignment = align
    paragraph.paragraph_format.line_spacing = 1.5
    paragraph.paragraph_format.space_after = Pt(0)
    run = paragraph.add_run(text)
    run.bold = bold
    run.font.name = "Times New Roman"
    run.font.size = Pt(14)


def add_code_block(doc, title, code_text, comments=None):
    add_text(doc, title, bold=True)
    if comments:
        for comment in comments:
            add_text(doc, comment, size=10, spacing=1.0, font="Courier New")
    for line in code_text.splitlines():
        add_text(doc, line, size=10, spacing=1.0, font="Courier New")
    add_blank(doc, 1)


def code_text(path):
    return path.read_text(encoding="utf-8")


def make_doc():
    doc = Document()
    for section in doc.sections:
        section.top_margin = Cm(2)
        section.bottom_margin = Cm(2)
        section.left_margin = Cm(2.5)
        section.right_margin = Cm(1.5)

    normal = doc.styles["Normal"]
    normal.font.name = "Times New Roman"
    normal.font.size = Pt(14)

    # Титульний аркуш
    add_blank(doc, 3)
    add_text(doc, "МІНІСТЕРСТВО ОСВІТИ І НАУКИ УКРАЇНИ", align=WD_ALIGN_PARAGRAPH.CENTER, bold=True, spacing=1.0)
    add_text(doc, "«КИЇВСЬКИЙ НАЦІОНАЛЬНИЙ ЕКОНОМІЧНИЙ УНІВЕРСИТЕТ", align=WD_ALIGN_PARAGRAPH.CENTER, bold=True, spacing=1.0)
    add_text(doc, "ІМЕНІ В. ГЕТЬМАНА»", align=WD_ALIGN_PARAGRAPH.CENTER, bold=True, spacing=1.0)
    add_text(doc, "ІНСТИТУТ ІНФОРМАЦІЙНИХ ТЕХНОЛОГІЙ В ЕКОНОМІЦІ", align=WD_ALIGN_PARAGRAPH.CENTER, bold=True, spacing=1.0)
    add_text(doc, "КАФЕДРА КОМП’ЮТЕРНИХ НАУК", align=WD_ALIGN_PARAGRAPH.CENTER, bold=True, spacing=1.0)
    add_blank(doc, 4)
    add_text(doc, "Звіт", align=WD_ALIGN_PARAGRAPH.CENTER, bold=True, size=16, spacing=1.0)
    add_text(doc, "про лабораторну роботу №4", align=WD_ALIGN_PARAGRAPH.CENTER, bold=True, size=16, spacing=1.0)
    add_text(doc, "з дисципліни «Об’єктно-орієнтований аналіз та проєктування інформаційних систем»", align=WD_ALIGN_PARAGRAPH.CENTER, spacing=1.0)
    add_blank(doc, 2)
    add_text(doc, "на тему: Поведінкові патерни програмування", align=WD_ALIGN_PARAGRAPH.CENTER, bold=True, size=16, spacing=1.0)
    add_blank(doc, 6)

    sig = doc.add_table(rows=2, cols=2)
    set_table_borders_none(sig)
    fill_cell(sig.rows[0].cells[0], "Виконала студентка групи ІН-204\nкурсу 2, спеціальність F3")
    fill_cell(sig.rows[0].cells[1], "Юрченко Є.С.")
    fill_cell(sig.rows[1].cells[0], "Перевірив")
    fill_cell(sig.rows[1].cells[1], "Кіндзерський О. В.")
    add_blank(doc, 4)
    add_text(doc, "Київ 2026", align=WD_ALIGN_PARAGRAPH.CENTER, spacing=1.0)
    doc.add_page_break()

    # Зміст
    add_heading(doc, "ЗМІСТ", align=WD_ALIGN_PARAGRAPH.CENTER)
    for line in [
        "Вступ",
        "1. Теоретична частина",
        "   1.1. Структурний патерн Strategy",
        "   1.2. Структурний патерн State",
        "2. Практична частина",
        "   2.1. Аналіз проєкту з використанням патерну Strategy",
        "   2.2. Власний проєкт з використанням патерну State",
        "3. Вхідні та вихідні дані програми",
        "4. Аналіз отриманих результатів",
        "Висновки",
        "Додатки",
    ]:
        add_text(doc, line)

    doc.add_page_break()

    # Вступ
    add_heading(doc, "ВСТУП", align=WD_ALIGN_PARAGRAPH.CENTER)
    add_text(doc, "Напрям дослідження у цій лабораторній роботі полягає у вивченні та практичному застосуванні поведінкових патернів програмування. Метою роботи є отримання навичок аналізу шаблонів Strategy і State, а також створення власного проєкту з використанням поведінкового патерну.")
    add_text(doc, "Завдання навчальної практики: описати сутність, переваги, недоліки та можливості застосування патерну Strategy, описати структуру State, проаналізувати готовий приклад Strategy і розробити власний проєкт на бібліотечну тему із застосуванням State.")

    # Теорія
    add_heading(doc, "1. ТЕОРЕТИЧНА ЧАСТИНА", align=WD_ALIGN_PARAGRAPH.CENTER)
    add_heading(doc, "1.1. Структурний патерн Strategy")
    add_text(doc, "Strategy визначає сімейство алгоритмів, інкапсулює кожен з них і робить їх взаємозамінними. Контекст зберігає посилання на стратегію і викликає її метод під час виконання програми.")
    add_text(doc, "Переваги Strategy:", bold=True)
    for item in [
        "заміна алгоритму без зміни коду контексту",
        "просте розширення новими алгоритмами",
        "зменшення кількості умовних операторів",
    ]:
        add_text(doc, "• " + item)
    add_text(doc, "Недоліки Strategy:", bold=True)
    for item in [
        "збільшує кількість класів",
        "для простих задач може бути надлишковим",
        "контекст і стратегії потрібно узгоджувати між собою",
    ]:
        add_text(doc, "• " + item)
    add_text(doc, "Патерн доцільно застосовувати у калькуляторах, сортуванні, фільтрації та інших задачах, де алгоритм може змінюватися під час роботи програми.")

    add_heading(doc, "1.2. Структурний патерн State")
    add_text(doc, "State дозволяє об’єкту змінювати поведінку залежно від його внутрішнього стану. При цьому створюється окремий клас для кожного стану, а сам контекст делегує поведінку поточному стану.")
    add_text(doc, "Структура State:", bold=True)
    for item in ["Context", "State", "ConcreteState"]:
        add_text(doc, "• " + item)
    add_text(doc, "Патерн корисний тоді, коли об’єкт має багато варіантів поведінки, які залежать від стану: наприклад, книга може бути доступною, виданою або втраченою.")

    # Практика
    add_heading(doc, "2. ПРАКТИЧНА ЧАСТИНА", align=WD_ALIGN_PARAGRAPH.CENTER)
    add_heading(doc, "2.1. Аналіз проєкту з використанням патерну Strategy")
    add_text(doc, "У прикладному проєкті Strategy реалізовано простий калькулятор. Клас ContextStrategy зберігає посилання на поточну стратегію, а класи StrategyAdd, StrategySub, StrategyMultiply і StrategyDiv реалізують окремі операції.")

    strat_table = doc.add_table(rows=1, cols=4)
    strat_table.style = "Table Grid"
    for cell, text in zip(strat_table.rows[0].cells, ["Клас", "Призначення", "Основні приватні поля", "Роль у патерні"]):
        fill_cell(cell, text, bold=True, align=WD_ALIGN_PARAGRAPH.CENTER)
    for row in [
        ["Strategy", "Оголошує інтерфейс для алгоритмів.", "-", "Базовий контракт для всіх стратегій."],
        ["ContextStrategy", "Зберігає та виконує поточну стратегію.", "strategy", "Контекст, який делегує обчислення."],
        ["StrategyAdd", "Виконує додавання.", "-", "Конкретна стратегія."],
        ["StrategySub", "Виконує віднімання.", "-", "Конкретна стратегія."],
        ["StrategyMultiply", "Виконує множення.", "-", "Конкретна стратегія."],
        ["StrategyDiv", "Виконує ділення.", "-", "Конкретна стратегія."],
    ]:
        cells = strat_table.add_row().cells
        for i, value in enumerate(row):
            fill_cell(cells[i], value)

    add_text(doc, "Вхідні дані для Strategy:", bold=True)
    add_text(doc, "• a = 10.5")
    add_text(doc, "• b = 20.5")
    add_text(doc, "• operation = sub")
    add_text(doc, "Вихідні дані для Strategy:", bold=True)
    strat_output = "-10.0"
    add_code_block(doc, "Результат виконання програми:", strat_output, comments=[
        "// Стратегія вибирається через оператор switch.",
        "// У цьому прикладі виконується операція віднімання.",
    ])
    add_text(doc, "Програма коректно вибирає потрібний алгоритм під час виконання. У прикладі використано стратегію віднімання, тому результат дорівнює -10.0.")

    add_heading(doc, "2.2. Власний проєкт з використанням патерну State")
    add_text(doc, "Власний проєкт реалізовано на темі бібліотеки. У ньому клас LibraryCopy є контекстом, а стани AvailableState, IssuedState і LostState описують різну поведінку одного й того ж примірника книги. Коли книга доступна, її можна видати читачеві; коли вона видана, її можна повернути; якщо книгу втрачено, вона переходить у стан lost.")

    state_table = doc.add_table(rows=1, cols=4)
    state_table.style = "Table Grid"
    for cell, text in zip(state_table.rows[0].cells, ["Клас", "Призначення", "Основні приватні поля", "Асоціації"]):
        fill_cell(cell, text, bold=True, align=WD_ALIGN_PARAGRAPH.CENTER)
    for row in [
        ["BookState", "Оголошує дії для станів книги.", "-", "Базовий інтерфейс станів."],
        ["LibraryCopy", "Описує примірник книги і змінює свій стан.", "copyId, title, readerName, state", "Містить поточний стан книги."],
        ["AvailableState", "Поводження для доступної книги.", "-", "Переходить у IssuedState або LostState."],
        ["IssuedState", "Поводження для виданої книги.", "-", "Повертає книгу в AvailableState або переводить у LostState."],
        ["LostState", "Поводження для втраченої книги.", "-", "Фінальний стан без переходів."],
        ["Main", "Демонструє зміну станів.", "-", "Створює об’єкт LibraryCopy і викликає методи станів."],
    ]:
        cells = state_table.add_row().cells
        for i, value in enumerate(row):
            fill_cell(cells[i], value)

    add_text(doc, "Вхідні дані для State:", bold=True)
    add_text(doc, "• один примірник книги з назвою Kobzar")
    add_text(doc, "• ім’я першого читача: Maria Ivanenko")
    add_text(doc, "• ім’я другого читача: Oksana Petrenko")
    add_text(doc, "Вихідні дані для State:", bold=True)
    state_output = """BookCopy{copyId=1, title='Kobzar', readerName='null', state=available}
BookCopy{copyId=1, title='Kobzar', readerName='Maria Ivanenko', state=issued}
BookCopy{copyId=1, title='Kobzar', readerName='null', state=available}
BookCopy{copyId=1, title='Kobzar', readerName='Oksana Petrenko', state=lost}"""
    add_code_block(doc, "Результат виконання програми:", state_output, comments=[
        "// Книга переходить між станами доступна, видана і втрачена.",
        "// Поведінка змінюється залежно від поточного стану об’єкта.",
    ])
    add_text(doc, "Патерн State у цьому проєкті показує, як одна сутність може змінювати поведінку без великої кількості умовних операторів. Для бібліотеки це зручно, бо одна і та сама книга поводиться по-різному залежно від стану.")

    # Дані та аналіз
    add_heading(doc, "3. ВХІДНІ ТА ВИХІДНІ ДАНІ ПРОГРАМИ", align=WD_ALIGN_PARAGRAPH.CENTER)
    add_text(doc, "Вхідними даними для Strategy є два числа та назва операції. Вихідними даними є результат обраної математичної дії. Вхідними даними для State є примірник книги та імена читачів. Вихідними даними є послідовність станів книги під час її видачі, повернення та позначення втрати.")
    add_text(doc, "Коротко про дані:", bold=True)
    add_text(doc, "• Strategy: a = 10.5, b = 20.5, operation = sub, результат = -10.0.")
    add_text(doc, "• State: примірник книги переходить між станами available, issued і lost.")

    add_heading(doc, "4. АНАЛІЗ ОТРИМАНИХ РЕЗУЛЬТАТІВ", align=WD_ALIGN_PARAGRAPH.CENTER)
    add_text(doc, "Під час проєктування було видно, що Strategy зручний тоді, коли потрібно швидко змінювати алгоритм без переписування контексту. У прикладі калькулятора це дозволило легко перемикати операції додавання, віднімання, множення і ділення.")
    add_text(doc, "Патерн State добре підійшов для бібліотечної теми, тому що стан книги дійсно впливає на її поведінку. Замість великої кількості умов кожен стан винесено в окремий клас. Це спростило логіку та зробило код зрозумілішим.")

    add_heading(doc, "ВИСНОВКИ", align=WD_ALIGN_PARAGRAPH.CENTER)
    add_text(doc, "Під час виконання лабораторної роботи було опрацьовано сутність поведінкових патернів Strategy і State. Було проаналізовано приклад калькулятора з використанням Strategy і розроблено власний проєкт на темі бібліотеки з використанням State. У програмі реалізовано класи з приватними полями, методами, коментарями та перевірено їхню роботу під час тестування. Мета лабораторної роботи досягнута.")

    # Додатки
    add_heading(doc, "ДОДАТКИ", align=WD_ALIGN_PARAGRAPH.CENTER)
    appendices = [
        ("ДОДАТОК А КОД ІНТЕРФЕЙСУ STRATEGY", BASE / "strategy" / "src" / "Strategy.java", ["// Інтерфейс для всіх математичних алгоритмів."]),
        ("ДОДАТОК Б КОД КЛАСУ CONTEXTSTRATEGY", BASE / "strategy" / "src" / "ContextStrategy.java", ["// Контекст, що зберігає поточну стратегію."]),
        ("ДОДАТОК В КОД КЛАСУ STRATEGYADD", BASE / "strategy" / "src" / "StrategyAdd.java", ["// Конкретна стратегія додавання."]),
        ("ДОДАТОК Г КОД КЛАСУ STRATEGYSUB", BASE / "strategy" / "src" / "StrategySub.java", ["// Конкретна стратегія віднімання."]),
        ("ДОДАТОК Д КОД КЛАСУ STRATEGYMULTIPLY", BASE / "strategy" / "src" / "StrategyMultiply.java", ["// Конкретна стратегія множення."]),
        ("ДОДАТОК Е КОД КЛАСУ STRATEGYDIV", BASE / "strategy" / "src" / "StrategyDiv.java", ["// Конкретна стратегія ділення."]),
        ("ДОДАТОК Є КОД КЛАСУ MAIN (STRATEGY)", BASE / "strategy" / "src" / "Main.java", ["// Тестовий клас калькулятора на Strategy."]),
        ("ДОДАТОК Ж КОД ІНТЕРФЕЙСУ BOOKSTATE", BASE / "state" / "src" / "BookState.java", ["// Інтерфейс для станів книги."]),
        ("ДОДАТОК З КОД КЛАСУ LIBRARYCOPY", BASE / "state" / "src" / "LibraryCopy.java", ["// Контекст, що змінює стан примірника книги."]),
        ("ДОДАТОК И КОД КЛАСУ AVAILABLESTATE", BASE / "state" / "src" / "AvailableState.java", ["// Стан, коли книга доступна для видачі."]),
        ("ДОДАТОК І КОД КЛАСУ ISSUEDSTATE", BASE / "state" / "src" / "IssuedState.java", ["// Стан, коли книга видана читачеві."]),
        ("ДОДАТОК К КОД КЛАСУ LOSTSTATE", BASE / "state" / "src" / "LostState.java", ["// Стан, коли книга втрачена."]),
        ("ДОДАТОК Л КОД КЛАСУ MAIN (STATE)", BASE / "state" / "src" / "Main.java", ["// Тестовий клас демонстрації змін стану книги."]),
    ]
    for title, path, comments in appendices:
        add_code_block(doc, title, code_text(path), comments=comments)

    REPORT.parent.mkdir(parents=True, exist_ok=True)
    doc.save(REPORT)
    return REPORT


if __name__ == "__main__":
    print(make_doc())
