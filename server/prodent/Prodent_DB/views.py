from django.shortcuts import render
from django.http import HttpResponse
from Prodent_DB.models import DB_access
import json
# Create your views here.

def testView(request):
    return HttpResponse('<h1>dbView!</h1>')


def getWhole(request, tag='Seo'):
    
    entries = DB_access.objects.get(Name = tag)

    data = entries.dic()

    return HttpResponse(json.dumps(data), content_type="application/json")
