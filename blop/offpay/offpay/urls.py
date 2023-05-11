"""offpay URL Configuration

The `urlpatterns` list routes URLs to views. For more information please see:
    https://docs.djangoproject.com/en/4.0/topics/http/urls/
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
from blop import views
urlpatterns = [
    path('admin/', admin.site.urls),
    path('',views.home_page),
    path('buypage', views.pay_page),
    path('sellpage', views.sell_page),
    path('updatepage', views.transaction_page),
    path('newpage',views.test),
    path('datapage',views.data_display),
]
