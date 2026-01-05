<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Add coin</title>
</head>
<body>
<h2>Add coin/banknote</h2>

<form action="coin-save" method="post" enctype="multipart/form-data">

    <label>Type:</label>
    <select name="type">
        <option value="COIN">COIN</option>
        <option value="BANKNOTE">BANKNOTE</option>
    </select>
    <br/><br/>

    <label>Country:</label>
    <input type="text" name="country" required />
    <br/><br/>

    <label>Denomination:</label>
    <input type="text" name="denomination" required />
    <br/><br/>

    <label>Currency:</label>
    <input type="text" name="currency" value="BGN" required />
    <br/><br/>

    <label>Year:</label>
    <input type="number" name="coinYear" min="0" max="3000" required />
    <br/><br/>

    <label>Notes:</label>
    <input type="text" name="notes" />
    <br/><br/>
<label>Front image:</label>
<input type="file" name="frontFile" accept="image/*" onchange="preview(this,'frontPrev')">
<br>
<img id="frontPrev" style="max-width:180px;display:none;border:1px solid #ddd;padding:4px">

<br><br>

<label>Back image:</label>
<input type="file" name="backFile" accept="image/*" onchange="preview(this,'backPrev')">
<br>
<img id="backPrev" style="max-width:180px;display:none;border:1px solid #ddd;padding:4px">

<script>
function preview(input, imgId){
  const img = document.getElementById(imgId);
  if(!input.files || !input.files[0]) { img.style.display='none'; return; }
  const r = new FileReader();
  r.onload = e => { img.src = e.target.result; img.style.display='block'; };
  r.readAsDataURL(input.files[0]);
}
</script>


    <button type="submit">Save</button>
    <a href="coins">Back</a>
</form>

</body>
</html>
