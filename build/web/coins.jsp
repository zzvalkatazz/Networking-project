<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.*"%>
<%@page import="bg.coincatalog.model.CatalogItem"%>

<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Coins</title>
    </head>
    <body>
        <h2>Coin Catalog</h2>
        <a href="coinForm.jsp">Add new</a>
        <form method="get" action="coins">
    Country:
    <input type="text" name="country" value="<%= request.getAttribute("country") == null ? "" : request.getAttribute("country") %>"/>
     Type:
  <select name="type">
    <option value="" <%= request.getAttribute("type")==null || "".equals(request.getAttribute("type")) ? "selected" : "" %>>All</option>
    <option value="COIN" <%= "COIN".equals(request.getAttribute("type")) ? "selected" : "" %>>COIN</option>
    <option value="BANKNOTE" <%= "BANKNOTE".equals(request.getAttribute("type")) ? "selected" : "" %>>BANKNOTE</option>
  </select>
  <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
Decade:
<select name="decade">
    <option value="">All</option>
    <c:forEach var="d" items="${decades}">
        <option value="${d}" <c:if test="${param.decade == d}">selected</c:if>>
            ${d}s
        </option>
    </c:forEach>
</select>

  Sort year:
  <select name="sort">
    <option value="year_asc"  <%= "year_asc".equals(request.getAttribute("sort")) ? "selected" : "" %>>Year ↑</option>
    <option value="year_desc" <%= "year_desc".equals(request.getAttribute("sort")) ? "selected" : "" %>>Year ↓</option>
  </select>
    <button type="submit">Filter</button>
    <a href="coins">Reset</a>
</form>
<br/>
        <table border="1" cellpadding="6">
    <tr>
        <th>ID</th>
        <th>Type</th>
        <th>Country</th>
        <th>Denomination</th>
        <th>Currency</th>
        <th>Year</th>
        <th>Notes</th>
        <th>Front</th>
        <th>Back</th>
        <th>Actions</th>
    </tr>
    
    <%
    List<CatalogItem> coins = (List<CatalogItem>) request.getAttribute("coins");
    if (coins != null) {
        for (CatalogItem c : coins) {
    %>
      <tr>
        <td><%= c.getId() %></td>
        <td><%= c.getType() %></td>
        <td><%= c.getCountry() %></td>
        <td><%= c.getDenomination() %></td>
        <td><%= c.getCurrency() %></td>
        <td><%= c.getCoinYear() %></td>
        <td><%= c.getNotes() == null ? "" : c.getNotes() %></td>
<td>
  <% if (c.getImageFront()!=null) { %>
        <img
      src="img?name=<%= c.getImageFront() %>"
      style="max-width:90px;cursor:pointer"
      onclick="openImg(this.src)"
      alt="front"
    />
  <% } else { %>(no)<% } %>
</td>

<td>
  <% if (c.getImageBack()!=null) { %>
        <img
      src="img?name=<%= c.getImageBack() %>"
      style="max-width:90px;cursor:pointer"
      onclick="openImg(this.src)"
      alt="front"
    />
  <% } else { %>(no)<% } %>
</td>



        <td>
    <form method="post" action="coin-delete" style="margin:0;">
        <input type="hidden" name="id" value="<%= c.getId() %>" />
        <button type="submit" onclick="return confirm('Delete this item?');">Delete</button>
    </form>
</td>

    </tr>
<%
        }
    }
%>
</table>
<div id="imgModal" onclick="closeImg()" style="
  display:none;
  position:fixed;
  left:0; top:0;
  width:100%; height:100%;
  background:rgba(0,0,0,0.75);
  z-index:9999;
  align-items:center;
  justify-content:center;
">
  <img id="imgModalPic" style="
    max-width:90%;
    max-height:90%;
    background:#fff;
    padding:8px;
    border-radius:6px;
    box-shadow:0 0 20px rgba(0,0,0,0.5);
  ">
</div>

<script>
function openImg(src){
  const modal = document.getElementById('imgModal');
  const pic = document.getElementById('imgModalPic');
  pic.src = src;
  modal.style.display = 'flex';
}

function closeImg(){
  document.getElementById('imgModal').style.display = 'none';
  document.getElementById('imgModalPic').src = '';
}


document.addEventListener('keydown', function(e){
  if(e.key === 'Escape') closeImg();
});
</script>
</body>
</html>