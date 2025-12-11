package Models;

import Views.PanelAdmin;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.sun.prism.paint.Color;
import java.awt.Desktop;
import java.awt.HeadlessException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileSystemView;
import org.json.JSONObject;

public class ProductosDao {

    Conexion cn = new Conexion();
    Connection con;
    PreparedStatement ps;
    ResultSet rs;

    public String registrar(Productos pro) {
        String consulta = "SELECT * FROM productos WHERE Codigo = ?";
        String sql = "INSERT INTO productos (Codigo, Descripcion, Precio_compra, Precio_venta, ID_proveedor, ID_medida, ID_categoria) VALUES (?,?,?,?,?,?,?)";
        try {
            con = cn.getConexion();
            ps = con.prepareStatement(consulta);
            ps.setString(1, pro.getCodigo());
            rs = ps.executeQuery();
            if (rs.next()) {
                return "Existe";
            } else {
                ps = con.prepareStatement(sql);
                ps.setString(1, pro.getCodigo());
                ps.setString(2, pro.getDescripcion());
                ps.setDouble(3, pro.getPrecio_compra());
                ps.setDouble(4, pro.getPrecio_venta());
                ps.setInt(5, pro.getId_proveedor());
                ps.setInt(6, pro.getId_medida());
                ps.setInt(7, pro.getId_categoria());
                ps.execute();
                return "Registrado";
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.toString());
            return "Error";
        }
    }

    public List ListaProductos(String valor) {
        List<Productos> listaPro = new ArrayList();
        String sql = "SELECT * FROM productos";
        String buscar = "SELECT * FROM productos WHERE Codigo LIKE '%" + valor + "%' OR Descripcion LIKE '%" + valor + "%'";
        try {
            con = cn.getConexion();
            if (valor.equalsIgnoreCase("")) {
                ps = con.prepareStatement(sql);
                rs = ps.executeQuery();
            } else {
                ps = con.prepareStatement(buscar);
                rs = ps.executeQuery();
            }

            while (rs.next()) {
                Productos pro = new Productos();
                pro.setId(rs.getInt("Id"));
                pro.setCodigo(rs.getString("Codigo"));
                pro.setDescripcion(rs.getString("Descripcion"));
                pro.setPrecio_venta(rs.getInt("Precio_venta"));
                pro.setCantidad(rs.getInt("Cantidad"));
                pro.setEstado(rs.getString("Estado"));
                listaPro.add(pro);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.toString());
        }
        return listaPro;
    }

    public String modificar(Productos pro) {
        String consulta = "SELECT * FROM productos WHERE Codigo = ? AND ID != ?";
        String sql = "UPDATE productos SET Codigo = ?, Descripcion = ?, Precio_compra = ?, Precio_venta = ?, ID_proveedor = ?, ID_medida = ?, ID_categoria = ? WHERE ID = ?";
        try {
            con = cn.getConexion();
            ps = con.prepareStatement(consulta);
            ps.setString(1, pro.getCodigo());
            ps.setInt(2, pro.getId());
            rs = ps.executeQuery();
            if (rs.next()) {
                return "Existe";
            } else {
                ps = con.prepareStatement(sql);
                ps.setString(1, pro.getCodigo());
                ps.setString(2, pro.getDescripcion());
                ps.setDouble(3, pro.getPrecio_compra());
                ps.setDouble(4, pro.getPrecio_venta());
                ps.setInt(5, pro.getId_proveedor());
                ps.setInt(6, pro.getId_medida());
                ps.setInt(7, pro.getId_categoria());
                ps.setInt(8, pro.getId());
                ps.execute();
                return "Modificado";
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.toString());
            return "Error";
        }
    }

    public boolean accion(String estado, int id) {
        String sql = "UPDATE productos SET Estado = ? WHERE ID = ?";
        try {
            con = cn.getConexion();
            ps = con.prepareStatement(sql);
            ps.setString(1, estado);
            ps.setInt(2, id);
            ps.execute();
            return true;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.toString());
        }
        return false;
    }

    public Productos buscarPro(int id) {
        String sql = "select p.*, pr.ID, pr.Proveedor,m.ID, m.Medida, c.ID, c.Categoria from productos p inner join proveedor pr ON p.ID_proveedor = pr.ID inner join medidas m ON p.ID_medida = m.ID inner join categorias c On p.ID_categoria = c.ID where p.ID = ?";
        Productos pro = new Productos();
        try {
            con = cn.getConexion();
            ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                pro.setCodigo(rs.getString("Codigo"));
                pro.setDescripcion(rs.getString("Descripcion"));
                pro.setPrecio_compra(rs.getDouble("Precio_compra"));
                pro.setPrecio_venta(rs.getDouble("Precio_venta"));
                pro.setId_proveedor(rs.getInt("ID_proveedor"));
                pro.setId_medida(rs.getInt("ID_medida"));
                pro.setId_categoria(rs.getInt("ID_categoria"));
                pro.setProveedor(rs.getString("Proveedor"));
                pro.setMedida(rs.getString("Medida"));
                pro.setCat(rs.getString("Categoria"));
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        return pro;
    }

    public Productos buscarCodigo(String codigo) {
        String sql = "SELECT * FROM productos WHERE Codigo = ? AND Estado = 'Activo'";
        Productos pro = new Productos();
        try {
            con = cn.getConexion();
            ps = con.prepareStatement(sql);
            ps.setString(1, codigo);
            rs = ps.executeQuery();
            if (rs.next()) {
                pro.setId(rs.getInt("Id"));
                pro.setDescripcion(rs.getString("Descripcion"));
                pro.setPrecio_venta(rs.getDouble("Precio_venta"));
                pro.setPrecio_compra(rs.getDouble("Precio_compra"));
                pro.setCantidad(rs.getInt("Cantidad"));
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        return pro;
    }
    
    public Productos buscarDescripcion(String desc) {
        String sql = "SELECT * FROM productos WHERE Descripcion = ? AND Estado = 'Activo'";
        Productos pro = new Productos();
        try {
            con = cn.getConexion();
            ps = con.prepareStatement(sql);
            ps.setString(1, desc);
            rs = ps.executeQuery();
            if (rs.next()) {
                pro.setId(rs.getInt("Id"));
                pro.setCodigo(rs.getString("Codigo"));
                pro.setPrecio_venta(rs.getDouble("Precio_venta"));
                pro.setPrecio_compra(rs.getDouble("Precio_compra"));
                pro.setCantidad(rs.getInt("Cantidad"));
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        return pro;
    }

    public Productos buscarId(int id) {
        String sql = "SELECT * FROM productos WHERE ID = ?";
        Productos pro = new Productos();
        try {
            con = cn.getConexion();
            ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                pro.setCantidad(rs.getInt("Cantidad"));
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        return pro;
    }

    //Modulos Compras
    public boolean registrarCompra(int id, String total) {
        String sql = "INSERT INTO compras (ID_proveedor, Total) VALUES (?,?)";
        try {
            con = cn.getConexion();
            ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ps.setString(2, total);
            ps.execute();
            return true;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
            return false;
        }
    }

    public boolean registrarCompraDetalle(int id_compra, int id, double precio, int cant, double sub_total) {
        String sql = "INSERT INTO detalle_compra (ID_compra, ID_producto, Precio, Cantidad, Subtotal) VALUES (?,?,?,?,?)";
        try {
            con = cn.getConexion();
            ps = con.prepareStatement(sql);
            ps.setInt(1, id_compra);
            ps.setInt(2, id);
            ps.setDouble(3, precio);
            ps.setInt(4, cant);
            ps.setDouble(5, sub_total);
            ps.execute();
            return true;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
            return false;
        }
    }

    public boolean actualizarStock(int cant, int id) {
        String sql = "UPDATE productos SET Cantidad = ? WHERE ID = ?";
        try {
            con = cn.getConexion();
            ps = con.prepareStatement(sql);
            ps.setInt(1, cant);
            ps.setInt(2, id);
            ps.execute();
            return true;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.toString());
            return false;
        }
    }

    public int getUltimoId(String tabla) {
        int id = 0;
        String sql = "SELECT MAX(ID) AS id FROM " + tabla;
        try {
            con = cn.getConexion();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            if (rs.next()) {
                id = rs.getInt("Id");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return id;
    }

    public List ListaDetalle(int id_compra) {
        List<Productos> listaPro = new ArrayList();
        String sql = "SELECT c.*, d.*, p.ID, p.Descripcion FROM compras c INNER JOIN detalle_compra d ON d.ID_compra = c.ID INNER JOIN productos p ON p.ID = d.ID_producto WHERE c.ID = ?";
        try {
            con = cn.getConexion();
            ps = con.prepareStatement(sql);
            ps.setInt(1, id_compra);
            rs = ps.executeQuery();
            while (rs.next()) {
                Productos pro = new Productos();
                pro.setCantidad(rs.getInt("Cantidad"));
                pro.setDescripcion(rs.getString("Descripcion"));
                pro.setPrecio_compra(rs.getDouble("Precio"));
                pro.setPrecio_venta(rs.getDouble("Subtotal"));
                listaPro.add(pro);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.toString());
        }
        return listaPro;
    }

    public void generarReporte(int id_compra) {
        double exentoC = 0.00;
        String fecha = "";
        String nomProveedor = "";
        String dirProveedor = "";
        String telProveedor = "";
        String corrProveedor = "";
        String mensaje = "";
        double IVAC;
        double totalConIvaC;
        double dolaresC;   
        double priceDollar = 0;
        
        try {
            URL url = new URL("https://api.dolarvzla.com/public/exchange-rate");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36");
            conn.setRequestMethod("GET");

            int responseCode = conn.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();             
                   
                String responseString = response.toString();
                JSONObject jsonObjectResponse = new JSONObject(responseString);
                JSONObject jsonObjectCurrent = jsonObjectResponse.getJSONObject("current");
                
                priceDollar += jsonObjectCurrent.getDouble("usd");
            } else {
                System.out.println("Error en la solicitud: " + responseCode);
            }
            conn.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            Font negrita = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD, BaseColor.WHITE);
            String url = FileSystemView.getFileSystemView().getDefaultDirectory().getPath();
            FileOutputStream archivo;
            File salida = new File(url + File.separator + "compra.pdf");
            archivo = new FileOutputStream(salida);
            Document doc = new Document();
            PdfWriter writer1 = PdfWriter.getInstance(doc, archivo);
            doc.open();

            //Contenido del Documento
            PdfPTable empresa = new PdfPTable(4);
            empresa.setWidthPercentage(100);
            float[] tamañoEncabezado = new float[]{15f, 5f, 50f, 30f};
            empresa.setWidths(tamañoEncabezado);
            empresa.setHorizontalAlignment(Element.ALIGN_LEFT);
            empresa.getDefaultCell().setBorder(0);
            doc.add(Chunk.NEWLINE);

            //Capturar y agregar Logotipo
            Image img = Image.getInstance(getClass().getResource("/Img/ddc1.png"));
            empresa.addCell(img);
            empresa.addCell("");

            //Consulta los datos de la empresa
            String sql = "SELECT * FROM configuracion";
            try {
                con = cn.getConexion();
                ps = con.prepareStatement(sql);
                rs = ps.executeQuery();
                if (rs.next()) {
                    mensaje = rs.getString("Mensaje");
                    //Agregar los datos de la empresa
                    empresa.addCell("Nombre: " + rs.getString("Nombre") + "\nRIF: " + rs.getString("Ruc") + "\nTeléfono: " + rs.getString("Telefono") + "\nDireccion: " + rs.getString("Direccion") +  "\nCorreo: " + rs.getString("Correo") + "\nIG: " + rs.getString("IG"));
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, e.toString());
            }

            //Consulta los datos del proveedor
            String consultaProveedor = "SELECT p.Proveedor, p.Telefono, p.Correo, p.Direccion, c.Total, c.Fecha FROM compras c INNER JOIN proveedor p ON c.ID_proveedor = p.ID WHERE c.ID = " + id_compra;
            try {
                con = cn.getConexion();
                ps = con.prepareStatement(consultaProveedor);
                rs = ps.executeQuery();
                if (rs.next()) {
                    //Agregar los datos del proveedor
                    nomProveedor = rs.getString("Proveedor");
                    telProveedor = rs.getString("Telefono");
                    corrProveedor = rs.getString("Correo");
                    dirProveedor = rs.getString("Direccion");
                    exentoC = rs.getDouble("Total");
                    fecha = rs.getString("Fecha");
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, e.toString());
            }

            //Datos del comprador
            empresa.addCell("N° Compra: " + id_compra + "\nFecha: " + fecha);

            doc.add(empresa);
            doc.add(Chunk.NEWLINE);

            //Titulo proveedor
            Paragraph titProveedor = new Paragraph();
            titProveedor.add("Datos del proveedor");
            titProveedor.setAlignment(Element.ALIGN_CENTER);
            doc.add(titProveedor);
            doc.add(Chunk.NEWLINE);

            //Mostrar datos del proveedor
            PdfPTable proveedor = new PdfPTable(3);
            proveedor.setWidthPercentage(100);
            float[] tamañoProveedor = new float[]{40f, 20f, 40f};
            proveedor.setWidths(tamañoProveedor);
            proveedor.setHorizontalAlignment(Element.ALIGN_LEFT);
            proveedor.getDefaultCell().setBorder(0);

            //Encabezado Proveedor
            PdfPCell nomPr = new PdfPCell(new Phrase("Nombre", negrita));
            PdfPCell telPr = new PdfPCell(new Phrase("Teléfono", negrita));
            PdfPCell dirPr = new PdfPCell(new Phrase("Dirección", negrita));

            //Quitar bordes de los encabezados
            nomPr.setBorder(0);
            telPr.setBorder(0);
            dirPr.setBorder(0);

            //Background del encabezado
            nomPr.setBackgroundColor(BaseColor.BLACK);
            telPr.setBackgroundColor(BaseColor.BLACK);
            dirPr.setBackgroundColor(BaseColor.BLACK);

            //Agregar los encabezados del proveedor
            proveedor.addCell(nomPr);
            proveedor.addCell(telPr);
            proveedor.addCell(dirPr);

            //agregar datos de proveedor
            proveedor.addCell(nomProveedor);
            proveedor.addCell(telProveedor);
            proveedor.addCell(dirProveedor);

            doc.add(proveedor);
            doc.add(Chunk.NEWLINE);
            //Fin Proveedor

            //Titulo productos
            Paragraph titProducto = new Paragraph();
            titProducto.add("Detalles de la Compra");
            titProducto.setAlignment(Element.ALIGN_CENTER);
            doc.add(titProducto);
            doc.add(Chunk.NEWLINE);

            //Mostrar datos de los productos
            PdfPTable producto = new PdfPTable(4);
            producto.setWidthPercentage(100);
            float[] tamañoProducto = new float[]{50f, 10f, 20f, 20f};
            producto.setWidths(tamañoProducto);
            producto.setHorizontalAlignment(Element.ALIGN_LEFT);
            producto.getDefaultCell().setBorder(0);

            //Encabezado Proveedor
            PdfPCell desPro = new PdfPCell(new Phrase("Descripcion", negrita));
            PdfPCell cantPro = new PdfPCell(new Phrase("Cant.", negrita));
            PdfPCell precioPro = new PdfPCell(new Phrase("Precio", negrita));
            PdfPCell subPro = new PdfPCell(new Phrase("Subtotal", negrita));

            //Quitar bordes de los encabezados
            desPro.setBorder(0);
            cantPro.setBorder(0);
            precioPro.setBorder(0);
            subPro.setBorder(0);

            //Background del encabezado
            desPro.setBackgroundColor(BaseColor.BLACK);
            cantPro.setBackgroundColor(BaseColor.BLACK);
            precioPro.setBackgroundColor(BaseColor.BLACK);
            subPro.setBackgroundColor(BaseColor.BLACK);

            //Agregar los encabezados de los productos
            producto.addCell(desPro);
            producto.addCell(cantPro);
            producto.addCell(precioPro);
            producto.addCell(subPro);

            //Consulta los datos del producto
            String consultaProductos = "SELECT d.Precio, d.Cantidad, d.Subtotal, p.Descripcion FROM compras c INNER JOIN detalle_compra d ON c.ID = d.ID_compra INNER JOIN productos p ON d.ID_producto = p.ID WHERE c.ID = " + id_compra;
            try {
                con = cn.getConexion();
                ps = con.prepareStatement(consultaProductos);
                rs = ps.executeQuery();
                while (rs.next()) {
                    //Agregar los datos del producto
                    producto.addCell(rs.getString("Descripcion"));
                    producto.addCell(rs.getString("Cantidad"));
                    producto.addCell(rs.getString("Precio") + " Bs.");
                    producto.addCell(rs.getString("Subtotal") + " Bs.");
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, e.toString());
            }
            doc.add(producto);
            doc.add(Chunk.NEWLINE);
            //Fin detalle productos

            //Mostrar total a pagar
            Paragraph total = new Paragraph();
            Paragraph IvaC = new Paragraph();
            Paragraph totalAPagarC = new Paragraph();
            Paragraph DolaresC = new Paragraph();
            
            IVAC = (exentoC * 16)/100;
            totalConIvaC = exentoC + IVAC;
            dolaresC = (double) totalConIvaC/priceDollar;
            String dolareslimitadosC = String.format("%.2f", dolaresC);
            
            total.add("Exento: " + exentoC + " Bs.");
            total.setAlignment(Element.ALIGN_RIGHT);
            IvaC.add("IVA G16.00%: " + IVAC + " Bs.");
            IvaC.setAlignment(Element.ALIGN_RIGHT);
            totalAPagarC.add("Total a Pagar: " + totalConIvaC + " Bs.");
            totalAPagarC.setAlignment(Element.ALIGN_RIGHT);
            DolaresC.add("Equivalente en Divisas: " + dolareslimitadosC + " $$");
            DolaresC.setAlignment(Element.ALIGN_RIGHT);
            
            doc.add(total);
            doc.add(IvaC);
            doc.add(totalAPagarC);
            doc.add(DolaresC);
            doc.add(Chunk.NEWLINE);

            //Mostrar mensaje
            Paragraph agradecimiento = new Paragraph();
            agradecimiento.add(mensaje);
            agradecimiento.setAlignment(Element.ALIGN_CENTER);
            doc.add(agradecimiento);
            doc.add(Chunk.NEWLINE);
            
            //Boton Enviar WhatsApp
            String numProvWhatsCompra = "58" + limpiarNumero(telProveedor);
            
            String rutaFuente = "C:/Windows/Fonts/seguiemj.ttf";
            BaseFont emojiFont = BaseFont.createFont(rutaFuente, BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
            
            PdfContentByte canvas = writer1.getDirectContent();
            PushbuttonField botonWhats = new PushbuttonField(writer1, new Rectangle(395, 690, 480, 720), "btnClick");
            botonWhats.setText("Enviar factura 💬");
            botonWhats.setBackgroundColor(BaseColor.GREEN);
            botonWhats.setFont(emojiFont);
            botonWhats.setVisibility(PushbuttonField.VISIBLE_BUT_DOES_NOT_PRINT);

            PdfFormField campo = botonWhats.getField();
            
            if(numProvWhatsCompra.matches("\\d{12}")){
                campo.setAction(new PdfAction("https://wa.me/" + numProvWhatsCompra + "?text=Factura%20de%20Compra"));
            }else{
                campo.setAction(PdfAction.javaScript("app.alert('Número incompleto o mal escrito');", writer1));
            }
            
            writer1.addAnnotation(campo);
            //END BotonWhats

            doc.close();
            archivo.close();
            
            //Start EnviarFactCorreo
            ImageIcon icono = new ImageIcon(getClass().getResource("/Img/correo.png"));
            int resp =JOptionPane.showConfirmDialog(null, "¿Desea enviar la factura por correo electrónico?", "¿Enviar Factura?", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, icono);
            
            switch (resp){
                case 0:
                    if(corrProveedor.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")){
                        try{
                            EmailSender.enviarCorreoConAdjunto(corrProveedor, "Envio de Factura" , "<h2>Factura adjunta: </h2>", salida);
                        }catch(Exception e){
                            JOptionPane.showMessageDialog(null, e.toString());
                        }
                    }else{
                        JOptionPane.showMessageDialog(null, "El correo esta incompleto o mal escrito!", "ERROR", JOptionPane.ERROR_MESSAGE);
                    }
                    break;
                case 1:
                    break;
            }
            //END EnviarFactCorreo

            Desktop.getDesktop().open(salida);

        } catch (DocumentException | HeadlessException | IOException e) {

        }
    }

    //Modulos ventas
    public boolean registrarVenta(int id, String total, int id_user) {
        String sql = "INSERT INTO ventas (ID_cliente, Total, ID_user) VALUES (?,?,?)";
        try {
            con = cn.getConexion();
            ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ps.setString(2, total);
            ps.setInt(3, id_user);
            ps.execute();
            return true;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
            return false;
        }
    }

    public boolean registrarVentaDetalle(int id_venta, int id_producto, double precio, int cant, double sub_total) {
        String sql = "INSERT INTO detalle_ventas (ID_venta, ID_producto, Precio, Cantidad, Subtotal) VALUES (?,?,?,?,?)";
        try {
            con = cn.getConexion();
            ps = con.prepareStatement(sql);
            ps.setInt(1, id_venta);
            ps.setInt(2, id_producto);
            ps.setDouble(3, precio);
            ps.setInt(4, cant);
            ps.setDouble(5, sub_total);
            ps.execute();
            return true;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
            return false;
        }
    }

    public void generarReporteVenta(int id_venta) {
        double exento = 0.00;
        String fecha = "";
        String nomCliente = "";
        String dirCliente = "";
        String telCliente = "";
        String corrCliente = "";
        String nomVendedor = "";
        String cajaVendedor = "";
        String mensaje = "";
        double IVA;
        double totalConIva;
        double dolares;
        double priceDollar = 0;

        try {
            URL url = new URL("https://api.dolarvzla.com/public/exchange-rate");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36");
            conn.setRequestMethod("GET");

            int responseCode = conn.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();             
                   
                String responseString = response.toString();
                JSONObject jsonObjectResponse = new JSONObject(responseString);
                JSONObject jsonObjectCurrent = jsonObjectResponse.getJSONObject("current");
                
                priceDollar += jsonObjectCurrent.getDouble("usd");
            } else {
                System.out.println("Error en la solicitud: " + responseCode);
            }
            conn.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        try {
            Font negrita = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD, BaseColor.WHITE);
            String url = FileSystemView.getFileSystemView().getDefaultDirectory().getPath();
            FileOutputStream archivo;
            File salida = new File(url + File.separator + "venta.pdf");
            archivo = new FileOutputStream(salida);
            Document doc = new Document();
            PdfWriter writer = PdfWriter.getInstance(doc, archivo);
            doc.open();

            //Contenido del Documento
            PdfPTable empresa = new PdfPTable(5);
            empresa.setWidthPercentage(100);
            float[] tamañoEncabezado = new float[]{15f, 2f, 50f, 3f, 30f};
            empresa.setWidths(tamañoEncabezado);
            empresa.setHorizontalAlignment(Element.ALIGN_LEFT);
            empresa.getDefaultCell().setBorder(0);
            doc.add(Chunk.NEWLINE);

            //Capturar y agregar Logotipo
            Image img = Image.getInstance(getClass().getResource("/Img/ddc1.png"));
            empresa.addCell(img);
            empresa.addCell("");

            //Consulta los datos de la empresa
            String sql = "SELECT * FROM configuracion";
            try {
                con = cn.getConexion();
                ps = con.prepareStatement(sql);
                rs = ps.executeQuery();
                if (rs.next()) {
                    mensaje = rs.getString("Mensaje");
                    //Agregar los datos de la empresa
                    empresa.addCell("Nombre: " + rs.getString("Nombre") + "\nRIF: " + rs.getString("Ruc") + "\nTeléfono: " + rs.getString("Telefono") + "\nDireccion: " + rs.getString("Direccion") +  "\nCorreo: " + rs.getString("Correo") + "\nIG: " + rs.getString("IG"));
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, e.toString());
            }
            
            empresa.addCell("");

            //Consulta los datos del cliente
            String consultaCliente = "SELECT c.Nombre, c.Telefono, c.Correo, c.Direccion, v.Total, v.Fecha FROM ventas v INNER JOIN clientes c ON v.ID_cliente = c.ID WHERE v.ID = " + id_venta;
            try {
                con = cn.getConexion();
                ps = con.prepareStatement(consultaCliente);
                rs = ps.executeQuery();
                if (rs.next()) {
                    //Agregar los datos del cliente
                    nomCliente = rs.getString("Nombre");
                    telCliente = rs.getString("Telefono");
                    corrCliente = rs.getString("Correo");
                    dirCliente = rs.getString("Direccion");
                    exento = rs.getDouble("Total");
                    fecha = rs.getString("Fecha");
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, e.toString());
            }
            
            String consultaIDUser = "SELECT ID_user FROM ventas WHERE ID=?";
            String consultaVendedor = "SELECT u.Nombre, c.Nombre AS nombre_caja FROM usuarios u INNER JOIN cajas c ON u.ID_caja = c.ID WHERE u.ID=?";
            try {
                con = cn.getConexion();
                ps = con.prepareStatement(consultaIDUser);
                ps.setInt(1, id_venta);
                rs = ps.executeQuery();
                if (rs.next()) {
                    int idVendedor = rs.getInt("ID_user");
                    ps = con.prepareStatement(consultaVendedor);
                    ps.setInt(1, idVendedor);
                    rs = ps.executeQuery();
                    if(rs.next()){
                        nomVendedor = rs.getString("Nombre");
                        cajaVendedor = rs.getString("nombre_caja");
                    }
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, e.toString());
            }

            //Datos del vendedor
            empresa.addCell("N° Facturas: " + id_venta + "\nFecha: " + fecha + "\nCaja: " + cajaVendedor + "\nVendedor: " + nomVendedor);

            doc.add(empresa);
            doc.add(Chunk.NEWLINE);

            //Titulo cliente
            Paragraph titCliente = new Paragraph();
            titCliente.add("Datos del cliente");
            titCliente.setAlignment(Element.ALIGN_CENTER);
            doc.add(titCliente);
            doc.add(Chunk.NEWLINE);

            //Mostrar datos del cliente
            PdfPTable cliente = new PdfPTable(3);
            cliente.setWidthPercentage(100);
            float[] tamañoCliente = new float[]{40f, 20f, 40f};
            cliente.setWidths(tamañoCliente);
            cliente.setHorizontalAlignment(Element.ALIGN_LEFT);
            cliente.getDefaultCell().setBorder(0);

            //Encabezado Cliente
            PdfPCell nomCli = new PdfPCell(new Phrase("Nombre", negrita));
            PdfPCell telCli = new PdfPCell(new Phrase("Teléfono", negrita));
            PdfPCell dirCli = new PdfPCell(new Phrase("Dirección", negrita));

            //Quitar bordes de los encabezados
            nomCli.setBorder(0);
            telCli.setBorder(0);
            dirCli.setBorder(0);

            //Background del encabezado
            nomCli.setBackgroundColor(BaseColor.BLACK);
            telCli.setBackgroundColor(BaseColor.BLACK);
            dirCli.setBackgroundColor(BaseColor.BLACK);

            //Agregar los encabezados del cliente
            cliente.addCell(nomCli);
            cliente.addCell(telCli);
            cliente.addCell(dirCli);

            //agregar datos de los clientes
            cliente.addCell(nomCliente);
            cliente.addCell(telCliente);
            cliente.addCell(dirCliente);

            doc.add(cliente);
            doc.add(Chunk.NEWLINE);
            //Fin Cliente

            //Titulo productos
            Paragraph titProducto = new Paragraph();
            titProducto.add("Detalles de la Venta");
            titProducto.setAlignment(Element.ALIGN_CENTER);
            doc.add(titProducto);
            doc.add(Chunk.NEWLINE);

            //Mostrar datos de los productos
            PdfPTable producto = new PdfPTable(4);
            producto.setWidthPercentage(100);
            float[] tamañoProducto = new float[]{50f, 10f, 20f, 20f};
            producto.setWidths(tamañoProducto);
            producto.setHorizontalAlignment(Element.ALIGN_LEFT);
            producto.getDefaultCell().setBorder(0);

            //Encabezado Proveedor
            PdfPCell desPro = new PdfPCell(new Phrase("Descripcion", negrita));
            PdfPCell cantPro = new PdfPCell(new Phrase("Cant.", negrita));
            PdfPCell precioPro = new PdfPCell(new Phrase("Precio", negrita));
            PdfPCell subPro = new PdfPCell(new Phrase("Subtotal", negrita));

            //Quitar bordes de los encabezados
            desPro.setBorder(0);
            cantPro.setBorder(0);
            precioPro.setBorder(0);
            subPro.setBorder(0);

            //Background del encabezado
            desPro.setBackgroundColor(BaseColor.BLACK);
            cantPro.setBackgroundColor(BaseColor.BLACK);
            precioPro.setBackgroundColor(BaseColor.BLACK);
            subPro.setBackgroundColor(BaseColor.BLACK);

            //Agregar los encabezados de los productos
            producto.addCell(desPro);
            producto.addCell(cantPro);
            producto.addCell(precioPro);
            producto.addCell(subPro);

            //Consulta los datos del producto
            String consultaProductos = "SELECT d.Precio, d.Cantidad, d.Subtotal, p.Descripcion FROM ventas v INNER JOIN detalle_ventas d ON v.ID = d.ID_venta INNER JOIN productos p ON d.ID_producto = p.ID WHERE v.ID = " + id_venta;
            try {
                con = cn.getConexion();
                ps = con.prepareStatement(consultaProductos);
                rs = ps.executeQuery();
                while (rs.next()) {
                    //Agregar los datos del producto
                    producto.addCell(rs.getString("Descripcion"));
                    producto.addCell(rs.getString("Cantidad"));
                    producto.addCell(rs.getString("Precio") + " Bs.");
                    producto.addCell(rs.getString("Subtotal") + " Bs.");
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, e.toString());
            }
            doc.add(producto);
            doc.add(Chunk.NEWLINE);
            //Fin detalle productos

            //Mostrar total a pagar
            Paragraph total = new Paragraph();
            Paragraph Iva  = new Paragraph();
            Paragraph TotalAPagar  = new Paragraph();
            Paragraph Dolares  = new Paragraph();
            
            IVA = (exento * 16)/100;
            totalConIva = exento + IVA;
            dolares = (double) totalConIva/priceDollar;
            String dolareslimitados = String.format("%.2f", dolares);
            
            total.add("Exento: " + exento + " Bs.");
            total.setAlignment(Element.ALIGN_RIGHT);
            Iva.add("IVA G16.00%: " + IVA + " Bs.");
            Iva.setAlignment(Element.ALIGN_RIGHT);
            TotalAPagar.add("Total a Pagar: " + totalConIva + " Bs.");
            TotalAPagar.setAlignment(Element.ALIGN_RIGHT);
            Dolares.add("Equivalente en Divisas: " + dolareslimitados + " $$");
            Dolares.setAlignment(Element.ALIGN_RIGHT);
            doc.add(total);
            doc.add(Iva);
            doc.add(TotalAPagar);
            doc.add(Dolares);
            doc.add(Chunk.NEWLINE);

            //Mostrar mensaje
            Paragraph agradecimiento = new Paragraph();
            agradecimiento.add(mensaje);
            agradecimiento.setAlignment(Element.ALIGN_CENTER);
            doc.add(agradecimiento);
            doc.add(Chunk.NEWLINE);
            
            //Boton Enviar WhatsApp
            String numClienteWhats = "58" + limpiarNumero(telCliente);
            
            String rutaFuente = "C:/Windows/Fonts/seguiemj.ttf";
            BaseFont emojiFont = BaseFont.createFont(rutaFuente, BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
            
            PdfContentByte canvas = writer.getDirectContent();
            PushbuttonField botonWhats = new PushbuttonField(writer, new Rectangle(395, 690, 480, 720), "btnClick");
            botonWhats.setText("Enviar factura 💬");
            botonWhats.setBackgroundColor(BaseColor.GREEN);
            botonWhats.setFont(emojiFont);
            botonWhats.setVisibility(PushbuttonField.VISIBLE_BUT_DOES_NOT_PRINT);

            PdfFormField campo = botonWhats.getField();
            
            if(numClienteWhats.matches("\\d{12}")){
                campo.setAction(new PdfAction("https://wa.me/" + numClienteWhats + "?text=Factura%20de%20Venta"));
            }else{
                campo.setAction(PdfAction.javaScript("app.alert('Número incompleto o mal escrito');", writer));
            }
            
            writer.addAnnotation(campo);
            //END BotonWhats
 
            doc.close();
            archivo.close();
            
            //Start EnviarFactCorreo
            ImageIcon icono = new ImageIcon(getClass().getResource("/Img/correo.png"));
            int resp =JOptionPane.showConfirmDialog(null, "¿Desea enviar la factura por correo electrónico?", "¿Enviar Factura?", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, icono);
            
            switch (resp){
                case 0:
                    if(corrCliente.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")){
                        try{
                            EmailSender.enviarCorreoConAdjunto(corrCliente, "Envio de Factura" , "<h2>Factura adjunta: </h2>", salida);
                        }catch(Exception e){
                            JOptionPane.showMessageDialog(null, e.toString());
                        }
                    }else{
                        JOptionPane.showMessageDialog(null, "El correo esta incompleto o mal escrito!", "ERROR", JOptionPane.ERROR_MESSAGE);
                    }
                    break;
                case 1:
                    break;
            }
            //END EnviarFactCorreo

            Desktop.getDesktop().open(salida);

        } catch (DocumentException | HeadlessException | IOException e) {

        }
    }
    
    public int contarProductos(){
    int total = 0;
        String sql = "SELECT COUNT(*) AS total FROM productos";
        try {
            con = cn.getConexion();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            if(rs.next()){
                total = rs.getInt("total");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.toString());
        }
        return total;
    }
    
    public static String limpiarNumero(String numero) {
        return numero.startsWith("0") ? numero.substring(1) : numero;
    }
}
