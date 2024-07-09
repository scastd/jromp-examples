cmake_minimum_required(VERSION 3.9)
project(jromp-examples C)

set(CMAKE_C_STANDARD 11)

find_package(OpenMP REQUIRED)
if (OPENMP_FOUND)
    message(STATUS "OpenMP found")
    set(CMAKE_C_FLAGS "${CMAKE_C_FLAGS} ${OpenMP_C_FLAGS}")
    set(CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS} ${OpenMP_CXX_FLAGS}")
    set(CMAKE_EXE_LINKER_FLAGS "${CMAKE_EXE_LINKER_FLAGS} ${OpenMP_EXE_LINKER_FLAGS}")

    message(STATUS "CMAKE_C_FLAGS: ${CMAKE_C_FLAGS}")
else ()
    message(FATAL_ERROR "OpenMP not found")
endif ()

set(EXAMPLES_DIR src/main/c/jromp/examples)

add_executable(basic_usage ${EXAMPLES_DIR}/basic_usage.c)
add_executable(parallel_for_base ${EXAMPLES_DIR}/parallel_for_base.c)
add_executable(pi_calculation_with_reduction ${EXAMPLES_DIR}/pi_calculation_with_reduction.c)
