<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>

<aside class="left-sidebar">
    <!-- Sidebar scroll-->
    <div>
        <div
                class="brand-logo d-flex align-items-center justify-content-between">
            <a href="${initParam.mvcPath}/admin/dashboard" class="text-nowrap logo-img"> <img
                    src="${pageContext.request.contextPath}/views/admin/assets/images/logo.png"
                    class="ps-4" alt=""/>
            </a>
            <div
                    class="close-btn d-xl-none d-block sidebartoggler cursor-pointer"
                    id="sidebarCollapse">
                <i class="ti ti-x fs-8"></i>
            </div>
        </div>
        <!-- Sidebar navigation-->
        <nav class="sidebar-nav scroll-sidebar" data-simplebar="">
            <ul id="sidebarnav">

                <li class="nav-small-cap"><i
                        class="ti ti-dots nav-small-cap-icon fs-4"></i> <span
                        class="hide-menu">Trang Chủ</span></li>

                <li class="sidebar-item"><a class="sidebar-link"
                                            href="${initParam.mvcPath}/admin/dashboard" aria-expanded="false"> <span> <i
                        class="fa-solid fa-table-columns"></i>
					</span> <span class="hide-menu">Dashboard</span>
                </a></li>

                <li class="nav-small-cap"><i
                        class="ti ti-dots nav-small-cap-icon fs-4"></i> <span
                        class="hide-menu">QUẢN LÝ PHIM</span></li>

                <li class="sidebar-item"><a class="sidebar-link"
                                            href="${initParam.mvcPath}/admin/videos" aria-expanded="false"> <span> <i class="fa-solid fa-video"></i>
					</span> <span class="hide-menu">Phim đang công chiếu</span>
                </a></li>

                <li class="sidebar-item"><a class="sidebar-link"
                                            href="${initParam.mvcPath}/admin/videos/disabled" aria-expanded="false"> <span> <i
                        class="fa-solid fa-ban"></i>
					</span> <span class="hide-menu">Phim ngừng công chiếu</span>
                </a></li>

                <li class="nav-small-cap"><i
                        class="ti ti-dots nav-small-cap-icon fs-4"></i> <span
                        class="hide-menu">QUẢN LÝ THỂ LOẠI PHIM</span></li>
                <li class="sidebar-item"><a class="sidebar-link"
                                            href="${initParam.mvcPath}/admin/categories"
                                            aria-expanded="false"> <span> <i class="fa-solid fa-list"></i>
					</span> <span class="hide-menu">Danh sách thể loại</span>
                </a></li>

                <li class="nav-small-cap"><i
                        class="ti ti-dots nav-small-cap-icon fs-4"></i> <span
                        class="hide-menu">QUẢN LÝ NGƯỜI DÙNG</span></li>

                <li class="sidebar-item"><a class="sidebar-link"
                                            href="${initParam.mvcPath}/admin/users" aria-expanded="false"> <span> <i class="fa-regular fa-address-book"></i>
					</span> <span class="hide-menu">Danh sách người dùng</span>
                </a></li>

                <li class="nav-small-cap"><i
                        class="ti ti-dots nav-small-cap-icon fs-4"></i> <span
                        class="hide-menu">BÁO CÁO - THỐNG KÊ</span></li>

                <li class="sidebar-item"><a class="sidebar-link"
                                            href="${initParam.mvcPath}/admin/videos/liked"
                                            aria-expanded="false"> <span> <i class="fa-solid fa-thumbs-up"></i>
					</span> <span class="hide-menu">Lượt thích từng video</span>
                </a></li>


                <li class="sidebar-item"><a class="sidebar-link"
                                            href="${initParam.mvcPath}/admin/topUsers" aria-expanded="false"> <span> <i class="fa-solid fa-money-bill"></i>
					</span> <span class="hide-menu">Top người dùng</span>
                </a></li>
            </ul>
        </nav>
        <!-- End Sidebar navigation -->
    </div>
    <!-- End Sidebar scroll-->
</aside>