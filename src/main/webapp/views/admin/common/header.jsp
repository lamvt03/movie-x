<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>

<header class="app-header">
    <nav class="navbar navbar-expand-lg navbar-light">
        <ul class="navbar-nav">
            <li class="nav-item d-block d-xl-none"><a
                    class="nav-link sidebartoggler nav-icon-hover" id="headerCollapse"
                    href="javascript:void(0)"> <i class="ti ti-menu-2"></i>
            </a>
            </li>
            <li class="nav-item"><a class="nav-link nav-icon-hover"
                                    href="javascript:void(0)"> <i class="ti ti-bell-ringing"></i>
                <div class="notification bg-primary rounded-circle"></div>
            </a></li>
        </ul>
        <strong>Xin chào ! ${sessionScope.currentAdmin.fullName} <span
                class="wave">👋</span></strong>
        <div class="navbar-collapse justify-content-end px-0" id="navbarNav">
            <ul
                    class="navbar-nav flex-row ms-auto align-items-center justify-content-end">
                <li class="nav-item dropdown"><a
                        class="nav-link nav-icon-hover" id="drop2"
                        data-bs-toggle="dropdown" aria-expanded="false"><img
                        src="${pageContext.request.contextPath}/views/admin/assets/images/user.jpg"
                        width="35" height="35" class="rounded-circle" alt=""/> </a>
                    <div
                            class="dropdown-menu dropdown-menu-end dropdown-menu-animate-up"
                            aria-labelledby="drop2">
                        <div class="message-body">
                            <a style="cursor: pointer;"
                               class="d-flex align-items-center gap-2 dropdown-item"> <i
                                    class="ti ti-user fs-6"></i>
                                <p class="mb-0 fs-3">Thông tin cá nhân</p>
                            </a> <a style="cursor: pointer;"
                                    class="d-flex align-items-center gap-2 dropdown-item"> <i
                                class="ti ti-replace fs-6"></i>
                            <p class="mb-0 fs-3">Đổi mật khẩu</p>
                        </a> <a href="${initParam.mvcPath}/admin/logout"
                                class="btn btn-outline-primary mx-3 mt-2 d-block">Đăng xuất</a>
                        </div>
                    </div>
                </li>
            </ul>
        </div>
    </nav>
</header>