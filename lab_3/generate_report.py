from pathlib import Path
from docx import Document
from docx.shared import Pt, Cm
from docx.enum.text import WD_ALIGN_PARAGRAPH
from docx.oxml import OxmlElement
from docx.oxml.ns import qn


BASE = Path(r"C:\Users\Asus\IdeaProjects\java_labs\lab_3")
REPORT = Path(r"C:\Users\Asus\OneDrive\Робочий стіл\університет\2 курс\2 семестр\джава\ІН-204_Юрченко_лр 3_ukr.docx")


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


def table_two_cells(doc, left, right):
    table = doc.add_table(rows=1, cols=2)
    set_table_borders_none(table)
    fill_cell(table.rows[0].cells[0], left)
    fill_cell(table.rows[0].cells[1], right)
    return table


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

    # Title page
    add_blank(doc, 3)
    add_text(doc, "МІНІСТЕРСТВО ОСВІТИ І НАУКИ УКРАЇНИ", align=WD_ALIGN_PARAGRAPH.CENTER, bold=True, spacing=1.0)
    add_text(doc, "«КИЇВСЬКИЙ НАЦІОНАЛЬНИЙ ЕКОНОМІЧНИЙ УНІВЕРСИТЕТ", align=WD_ALIGN_PARAGRAPH.CENTER, bold=True, spacing=1.0)
    add_text(doc, "ІМЕНІ В. ГЕТЬМАНА»", align=WD_ALIGN_PARAGRAPH.CENTER, bold=True, spacing=1.0)
    add_text(doc, "ІНСТИТУТ ІНФОРМАЦІЙНИХ ТЕХНОЛОГІЙ В ЕКОНОМІЦІ", align=WD_ALIGN_PARAGRAPH.CENTER, bold=True, spacing=1.0)
    add_text(doc, "КАФЕДРА КОМП’ЮТЕРНИХ НАУК", align=WD_ALIGN_PARAGRAPH.CENTER, bold=True, spacing=1.0)

    add_blank(doc, 4)
    add_text(doc, "Звіт", align=WD_ALIGN_PARAGRAPH.CENTER, bold=True, size=16, spacing=1.0)
    add_text(doc, "про лабораторну роботу №3", align=WD_ALIGN_PARAGRAPH.CENTER, bold=True, size=16, spacing=1.0)
    add_text(doc, "з дисципліни «Об’єктно-орієнтований аналіз та проєктування інформаційних систем»", align=WD_ALIGN_PARAGRAPH.CENTER, spacing=1.0)
    add_blank(doc, 2)
    add_text(doc, "на тему: Структурні патерни програмування", align=WD_ALIGN_PARAGRAPH.CENTER, bold=True, size=16, spacing=1.0)

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

    # Contents
    add_heading(doc, "ЗМІСТ", align=WD_ALIGN_PARAGRAPH.CENTER)
    for line in [
        "Вступ",
        "1. Теоретична частина",
        "   1.1. Структурний патерн Composite",
        "   1.2. Структурний патерн Decorator",
        "2. Практична частина",
        "   2.1. Аналіз проєкту з використанням патерну Flyweight",
        "   2.2. Власний проєкт з використанням патерну Composite",
        "3. Вхідні та вихідні дані програми",
        "4. Аналіз отриманих результатів",
        "Висновки",
        "Додатки",
    ]:
        add_text(doc, line)

    doc.add_page_break()

    # Introduction
    add_heading(doc, "ВСТУП", align=WD_ALIGN_PARAGRAPH.CENTER)
    add_text(doc, "Напрям дослідження у цій лабораторній роботі полягає у вивченні та практичному застосуванні структурних патернів програмування. Метою роботи є отримання навичок аналізу шаблонів Composite, Decorator і Flyweight, а також створення власного проєкту з використанням структурного патерну.")
    add_text(doc, "Завдання навчальної практики: описати сутність та можливості застосування патерну Composite, описати структуру Decorator, проаналізувати готовий приклад Flyweight і розробити власний проєкт на бібліотечну тему із застосуванням Composite.")

    # Theory
    add_heading(doc, "1. ТЕОРЕТИЧНА ЧАСТИНА", align=WD_ALIGN_PARAGRAPH.CENTER)
    add_heading(doc, "1.1. Структурний патерн Composite")
    add_text(doc, "Composite дозволяє об’єднувати об’єкти у деревоподібні структури та працювати з окремими об’єктами і групами об’єктів однаково. Листи дерева і композити мають спільний інтерфейс, тому клієнтському коду не потрібно розрізняти, де знаходиться одна сутність, а де ціла група елементів.")
    add_text(doc, "Переваги Composite:", bold=True)
    for item in [
        "однаковий спосіб роботи з окремими об’єктами і їхніми групами",
        "зручне обходження дерева",
        "легке розширення новими типами компонентів",
    ]:
        add_text(doc, "• " + item)
    add_text(doc, "Недоліки Composite:", bold=True)
    for item in [
        "складніша структура класів",
        "керування дочірніми елементами може бути незручним у великих ієрархіях",
        "непотрібний, якщо дерево об’єктів не потрібне",
    ]:
        add_text(doc, "• " + item)
    add_text(doc, "Патерн доцільно застосовувати у файлових системах, меню, каталогах і будь-яких ієрархічних структурах.")

    add_heading(doc, "1.2. Структурний патерн Decorator")
    add_text(doc, "Decorator дає змогу динамічно додавати об’єкту нову поведінку, обгортаючи його у спеціальний клас-обгортку. На відміну від наслідування, декоратори можна комбінувати у потрібному порядку під час виконання програми.")
    add_text(doc, "Структура Decorator:", bold=True)
    for item in ["Component", "ConcreteComponent", "Decorator", "ConcreteDecorator"]:
        add_text(doc, "• " + item)
    add_text(doc, "Патерн використовують тоді, коли потрібно додати поведінку без зміни початкового класу або коли кількість можливих комбінацій поведінки занадто велика для наслідування.")

    # Practice
    add_heading(doc, "2. ПРАКТИЧНА ЧАСТИНА", align=WD_ALIGN_PARAGRAPH.CENTER)
    add_heading(doc, "2.1. Аналіз проєкту з використанням патерну Flyweight")
    add_text(doc, "У прикладному проєкті Flyweight спільний стан книги винесено в об’єкт BookType, а окремий екземпляр книги представлено класом BookCopy. Фабрика BookTypeFactory зберігає вже створені типи книг і повертає той самий об’єкт для однакових даних.")

    fly_table = doc.add_table(rows=1, cols=4)
    fly_table.style = "Table Grid"
    for cell, text in zip(fly_table.rows[0].cells, ["Клас", "Призначення", "Основні приватні поля", "Роль у патерні"]):
        fill_cell(cell, text, bold=True, align=WD_ALIGN_PARAGRAPH.CENTER)
    for row in [
        ["BookType", "Містить спільні дані про тип книги.", "title, author, genre, publicationYear", "Flyweight-об’єкт зі спільним станом."],
        ["BookTypeFactory", "Повертає вже створені типи книг.", "TYPES", "Кешує та повторно використовує об’єкти."],
        ["BookCopy", "Описує окремий примірник книги.", "copyId, shelfLocation, available, bookType", "Зберігає зовнішній стан."],
        ["Main", "Створює типи книг і примірники та виводить результат.", "немає", "Демонструє роботу патерну."],
    ]:
        cells = fly_table.add_row().cells
        for i, value in enumerate(row):
            fill_cell(cells[i], value)

    add_text(doc, "Вхідні дані для Flyweight:", bold=True)
    add_text(doc, "• два об’єкти BookType: Kobzar і Zahar Berkut")
    add_text(doc, "• п’ять об’єктів BookCopy з різними номерами полиць і станом доступності")
    add_text(doc, "Вихідні дані для Flyweight:", bold=True)
    fly_output = """Copy 101 [A-1, available] -> Kobzar | Taras Shevchenko | Poetry | 1840
Copy 102 [A-2, available] -> Kobzar | Taras Shevchenko | Poetry | 1840
Copy 201 [B-1, available] -> Zahar Berkut | Ivan Franko | Novel | 1883
Copy 202 [B-2, issued] -> Zahar Berkut | Ivan Franko | Novel | 1883
Copy 203 [B-3, available] -> Zahar Berkut | Ivan Franko | Novel | 1883
Shared types: 2"""
    add_code_block(doc, "Результат виконання програми:", fly_output, comments=[
        "// Виведення кожного екземпляра книги та кількості спільних типів.",
        "// У прикладі видно, що однакові типи книг не дублюються.",
    ])
    add_text(doc, "У результаті створюється п’ять екземплярів, але спільних типів книг лише два. Це означає, що патерн Flyweight працює правильно і зменшує кількість дублюваних об’єктів.")

    add_heading(doc, "2.2. Власний проєкт з використанням патерну Composite")
    add_text(doc, "Власний проєкт реалізовано на темі бібліотеки. У ньому інтерфейс LibraryComponent задає спільну поведінку для вузлів дерева каталогу. Клас BookLeaf описує одну книгу, а CatalogComposite може містити інші каталоги або книги. У класі Main створюється дерево каталогу і виконується пошук книги за автором.")

    comp_table = doc.add_table(rows=1, cols=4)
    comp_table.style = "Table Grid"
    for cell, text in zip(comp_table.rows[0].cells, ["Клас", "Призначення", "Основні приватні поля", "Асоціації"]):
        fill_cell(cell, text, bold=True, align=WD_ALIGN_PARAGRAPH.CENTER)
    for row in [
        ["LibraryComponent", "Базовий інтерфейс для дерева каталогу.", "-", "Спільний тип для вузлів дерева."],
        ["BookLeaf", "Описує одну книгу.", "title, author, genre, publicationYear", "Лист дерева каталогу."],
        ["CatalogComposite", "Містить інші каталоги або книги.", "name, children", "Компонент, який може містити дочірні елементи."],
        ["Main", "Створює дерево і перевіряє пошук.", "-", "Використовує всі компоненти разом."],
    ]:
        cells = comp_table.add_row().cells
        for i, value in enumerate(row):
            fill_cell(cells[i], value)

    add_text(doc, "Вхідні дані для Composite:", bold=True)
    add_text(doc, "• кореневий каталог Library Catalog")
    add_text(doc, "• дві гілки: Poetry і Fiction")
    add_text(doc, "• чотири книги: Kobzar, Moysej, Zahar Berkut, Forest Song")
    add_text(doc, "• пошуковий запит: 'Franko'")
    add_text(doc, "Вихідні дані для Composite:", bold=True)
    comp_output = """Catalog tree:
Library Catalog
  Poetry
    Kobzar | Taras Shevchenko | Poetry | 1840
    Moysej | Ivan Franko | Poetry | 1905
  Fiction
    Zahar Berkut | Ivan Franko | Novel | 1883
    Forest Song | Lesya Ukrainka | Drama | 1911

Search result for 'Franko':
Moysej | Ivan Franko | Poetry | 1905
Zahar Berkut | Ivan Franko | Novel | 1883"""
    add_code_block(doc, "Результат виконання програми:", comp_output, comments=[
        "// Побудова дерева каталогу та виведення результатів пошуку.",
        "// Категорії та книги працюють як вузли одного дерева.",
    ])
    add_text(doc, "Патерн Composite у цьому проєкті дозволяє однаково працювати з категоріями каталогу і з окремими книгами. Пошук виконується по всьому дереву, а результат повертає лише ті книги, які відповідають запиту.")

    # Data and analysis
    add_heading(doc, "3. ВХІДНІ ТА ВИХІДНІ ДАНІ ПРОГРАМИ", align=WD_ALIGN_PARAGRAPH.CENTER)
    add_text(doc, "Для обох програм вхідними даними є об’єкти, створені у класі Main: для Flyweight це типи книг та їхні екземпляри, для Composite це структура каталогу та список книг. Вихідні дані формуються у вигляді текстового виводу в консоль: перелік екземплярів, список доступних книг, дерево каталогу та результати пошуку.")
    add_text(doc, "Коротко про дані:", bold=True)
    add_text(doc, "• Flyweight: вхідні дані складаються з 2 типів книг і 5 примірників; вихідні дані показують, що shared types = 2.")
    add_text(doc, "• Composite: вхідні дані складаються з дерева каталогу і пошукового запиту; вихідні дані показують дерево і знайдені книги.")

    add_heading(doc, "4. АНАЛІЗ ОТРИМАНИХ РЕЗУЛЬТАТІВ", align=WD_ALIGN_PARAGRAPH.CENTER)
    add_text(doc, "Під час проєктування було видно, що Flyweight добре підходить для ситуацій, де багато об’єктів мають однакову частину даних. У прикладі бібліотеки це дозволило створити багато екземплярів книг без дублювання типів книг. Composite, навпаки, зручний для ієрархій. У бібліотеці каталог може містити підкаталоги, а ті, у свою чергу, окремі книги.")
    add_text(doc, "Під час програмування й тестування програми показали коректний результат: пошук у Flyweight повертає об’єкти з повторним використанням типів, а Composite проходить по дереву каталогу і знаходить потрібні книги. Отримані результати підтверджують правильність вибору структурних патернів для поставлених задач.")

    add_heading(doc, "ВИСНОВКИ", align=WD_ALIGN_PARAGRAPH.CENTER)
    add_text(doc, "Під час виконання лабораторної роботи було опрацьовано сутність структурних патернів Composite, Decorator та Flyweight. Було проаналізовано приклад із патерном Flyweight і розроблено власний проєкт на темі бібліотеки з використанням Composite. У програмі реалізовано класи з приватними полями, конструкторами, методами get/set, а також перевірено роботу пошуку та видачі книг. Мета лабораторної роботи досягнута.")

    # Appendix
    add_heading(doc, "ДОДАТКИ", align=WD_ALIGN_PARAGRAPH.CENTER)
    appendices = [
        ("ДОДАТОК А КОД КЛАСУ BOOKTYPE", BASE / "flyweight" / "src" / "BookType.java", ["// Спільний об’єкт для збереження даних про тип книги."]),
        ("ДОДАТОК Б КОД КЛАСУ BOOKTYPEFACTORY", BASE / "flyweight" / "src" / "BookTypeFactory.java", ["// Фабрика повторно використовує однакові типи книг."]),
        ("ДОДАТОК В КОД КЛАСУ BOOKCOPY", BASE / "flyweight" / "src" / "BookCopy.java", ["// Окремий екземпляр книги з власним станом."]),
        ("ДОДАТОК Г КОД КЛАСУ MAIN (FLYWEIGHT)", BASE / "flyweight" / "src" / "Main.java", ["// Створення спільних типів і багатьох екземплярів.", "// Виведення результатів роботи у консоль."]),
        ("ДОДАТОК Д КОД ІНТЕРФЕЙСУ LIBRARYCOMPONENT", BASE / "composite" / "src" / "LibraryComponent.java", ["// Базовий компонент дерева каталогу."]),
        ("ДОДАТОК Е КОД КЛАСУ BOOKLEAF", BASE / "composite" / "src" / "BookLeaf.java", ["// Лист дерева каталогу, який відповідає одній книзі."]),
        ("ДОДАТОК Є КОД КЛАСУ CATALOGCOMPOSITE", BASE / "composite" / "src" / "CatalogComposite.java", ["// Композит, який може містити інші категорії або книги."]),
        ("ДОДАТОК Ж КОД КЛАСУ MAIN (COMPOSITE)", BASE / "composite" / "src" / "Main.java", ["// Побудова дерева каталогу та перевірка пошуку."]),
    ]
    for title, path, comments in appendices:
        add_code_block(doc, title, code_text(path), comments=comments)

    REPORT.parent.mkdir(parents=True, exist_ok=True)
    doc.save(REPORT)
    return REPORT


if __name__ == "__main__":
    print(make_doc())
