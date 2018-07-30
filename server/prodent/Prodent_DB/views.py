from django.shortcuts import render
from django.http import HttpResponse
from Prodent_DB.models import DB_access
import json
# Create your views here.

def testView(request):
    return HttpResponse('<h2>dbView!</h2>')


def getData(request, tag=None):
    
    entries = DB_access.objects.get(Name = tag)

    data = entries.dic()

    return HttpResponse(json.dumps(data), content_type="application/json")


def postData(request):
    
    #DB_access.objects.create();
    #data = json.loads(request.raw_post_data)
    
    return HttpResponse(request)
