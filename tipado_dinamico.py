# TIPADO DINAMICO - Python
# Este SI se ejecuta:  python3 tipado_dinamico.py

# 1. El tipo vive en el VALOR
x = 5
print(x, type(x))       # 5 <class 'int'>

x = "hola"
print(x, type(x))       # hola <class 'str'>

# 2. Dinamico pero FUERTE: se niega, no adivina
try:
    print("a" + 1)
except TypeError as e:
    print("Error:", e)  # solo se ve AL EJECUTAR

# JavaScript (F12):  "a" + 1  ->  "a1"    <- debil, improvisa
