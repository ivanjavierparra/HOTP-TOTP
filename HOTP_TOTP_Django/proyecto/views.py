from django.shortcuts import render


# Create your views here.
def index(request):
    print("Hola mundo...")
    return render(request, "index.html", {})

def registrarse(request):
    print("Hola mundo...")
    return render(request, "registrarse.html", {})

def bienvenido(request):
    print("Hola mundo...")
    return render(request, "bienvenido.html", {})