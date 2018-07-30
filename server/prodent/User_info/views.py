from django.shortcuts import render, redirect
from django.http import HttpResponse
from django.views.decorators.csrf import csrf_exempt

from .models import DB_access
from .forms import PostForm
import json

# Create your views here.

def testView(request):
    return HttpResponse('<h2>dbView!</h2>')


def getData(request, tag=None):
    try:
        entries = DB_access.objects.get(Uid = tag)
        data = entries.dic()
        return HttpResponse(json.dumps(data), content_type="application/json")
    except : 
        return HttpResponse("No Correct Data")
@csrf_exempt
def postData(request):
    if request.method == "POST":
        form = PostForm(request.POST)
        if form.is_valid():
            row = form.save(commit = False)
            row.generate()
            return HttpResponse("OK")
            
    else:
       return HttpResponse("Wrong Command Occur!")
