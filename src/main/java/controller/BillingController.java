package controller;

import com.google.gson.Gson;
import entity.Bill;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.BillingService;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

@WebServlet("/api/bill")
public class BillingController extends HttpServlet {
    private BillingService billingService = new BillingService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String supplierIdParam = req.getParameter("supplierId");
            if (supplierIdParam == null || supplierIdParam.trim().isEmpty()) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                resp.getWriter().write("{\"error\": \"supplierId is required\"}");
                return;
            }

            int supplierId = Integer.parseInt(supplierIdParam);
            List<Bill> bills = billingService.findAllBySupplierId(supplierId);

            Gson gson = new Gson();
            String jsonResponse = gson.toJson(bills);

            resp.setContentType("application/json");
            resp.setStatus(HttpServletResponse.SC_OK);
            resp.getWriter().write(jsonResponse);
        } catch (NumberFormatException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("{\"error\": \"Invalid supplierId format\"}");
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write("{\"error\": \"" + e.getMessage() + "\"}");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Gson gson = new Gson();
        BufferedReader reader = req.getReader();
        Bill bill = gson.fromJson(reader, Bill.class);

        if (bill == null) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("{\"error\": \"Invalid request body\"}");
            return;
        }

        try {
            Bill createdBill = billingService.save(bill);

            resp.setStatus(HttpServletResponse.SC_CREATED);
            resp.setContentType("application/json");
            String jsonResponse = gson.toJson(createdBill);
            resp.getWriter().write(jsonResponse);
        } catch (IllegalArgumentException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("{\"error\": \"" + e.getMessage() + "\"}");
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write("{\"error\": \"Something went wrong on the server\"}");
            e.printStackTrace();
        }
    }
}