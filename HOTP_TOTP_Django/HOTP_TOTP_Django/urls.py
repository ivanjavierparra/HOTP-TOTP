"""HOTP_TOTP_Django URL Configuration

The `urlpatterns` list routes URLs to views. For more information please see:
    https://docs.djangoproject.com/en/2.1/topics/http/urls/
Examples:
Function views
    1. Add an import:  from my_app import views
    2. Add a URL to urlpatterns:  path('', views.home, name='home')
Class-based views
    1. Add an import:  from other_app.views import Home
    2. Add a URL to urlpatterns:  path('', Home.as_view(), name='home')
Including another URLconf
    1. Import the include() function: from django.urls import include, path
    2. Add a URL to urlpatterns:  path('blog/', include('blog.urls'))
"""
from django.contrib import admin
from django.urls import path
from django.conf.urls import include, url
from . import views
from proyecto import views as proyectoviews

urlpatterns = [
    #url(r'^index$', views.index, name='index'),
    #url('', views.index, name='index')    
    url(r'^$', views.index, name='index'),
    url(r'^login$', views.login, name='login'),
    url(r'^registrarse$', proyectoviews.registrarse, name='registrarse'),
    url(r'^bienvenido$', proyectoviews.bienvenido, name='bienvenido')
]
