package controller;

import com.google.gson.Gson;
import dto.BillCreateDto;
import entity.Bill;
import exception.handler.AppExceptionHandler;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import manager.SingletonManager;
import service.BillingService;
import util.JsonResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

@WebServlet("/api/bill")
public class BillingController extends HttpServlet {
    private final BillingService billingService = SingletonManager.getBean(BillingService.class);
    private final Gson gson = new Gson();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            String retailerIdParam = req.getParameter("retailerId");
            String supplierIdParam = req.getParameter("supplierId");

            if (retailerIdParam != null && !retailerIdParam.trim().isEmpty()) {
                int retailerId = Integer.parseInt(retailerIdParam);

                List<Bill> bills = billingService.findAllByRetailerId(retailerId);
                JsonResponse.send(resp, bills, HttpServletResponse.SC_OK);
                return;
            }

            if (supplierIdParam != null && !supplierIdParam.trim().isEmpty()) {
                int supplierId = Integer.parseInt(supplierIdParam);

                List<Bill> bills = billingService.findAllBySupplierId(supplierId);
                JsonResponse.send(resp, bills, HttpServletResponse.SC_OK);
                return;
            }

            JsonResponse.send(resp, "Invalid query parameters", HttpServletResponse.SC_BAD_REQUEST);

        } catch (Exception e) {
            AppExceptionHandler.handle(resp, e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String action = req.getParameter("action");

        if ("save".equalsIgnoreCase(action)) {
            handleSave(req, resp);
        } else if ("create".equalsIgnoreCase(action)) {
            handleCreate(req, resp);
        } else {
            JsonResponse.send(resp, "Invalid action parameter", HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String action = req.getParameter("action");

        if ("confirm".equalsIgnoreCase(action)) {
            handleConfirm(req, resp);
        } else {
            JsonResponse.send(resp, "Invalid action parameter", HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    private void handleSave(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        BufferedReader reader = req.getReader();
        Bill bill = gson.fromJson(reader, Bill.class);

        if (bill == null) {
            JsonResponse.send(resp, "Invalid payload", HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        try {
            Bill createdBill = billingService.save(bill);
            JsonResponse.send(resp, createdBill, HttpServletResponse.SC_OK);
        } catch (Exception e) {
            AppExceptionHandler.handle(resp, e);
        }
    }

    private void handleCreate(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        BufferedReader reader = req.getReader();
        BillCreateDto payload = gson.fromJson(reader, BillCreateDto.class);

        if (payload == null || payload.getProducts() == null || payload.getProducts().isEmpty()) {
            JsonResponse.send(resp, "Invalid payload", HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        try {
            billingService.create(payload);
            JsonResponse.send(resp, "Bill created successfully", HttpServletResponse.SC_OK);
        } catch (Exception e) {
            AppExceptionHandler.handle(resp, e);
        }

    }

    private void handleConfirm(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String idParam = req.getParameter("id");

        if (idParam == null || idParam.trim().isEmpty()) {
            JsonResponse.send(resp, "Missing bill ID", HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        try {
            int billId = Integer.parseInt(idParam);

            billingService.confirm(billId);
            JsonResponse.send(resp, "Bill confirmed successfully", HttpServletResponse.SC_OK);
        } catch (NumberFormatException e) {
            JsonResponse.send(resp, "Invalid bill ID", HttpServletResponse.SC_BAD_REQUEST);
        } catch (Exception e) {
            AppExceptionHandler.handle(resp, e);
        }
    }
}